package com.example.imagesearch.ui

import SearchAdapter
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imagesearch.R
import com.example.imagesearch.databinding.ActivityMainBinding
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.system.Os.remove
import android.util.Base64
import java.io.ByteArrayOutputStream
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.data.Repository
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val selectedImages: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView 초기화
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        // 이미지 검색 프래그먼트와 내 보관함 프래그먼트를 숨김 상태로 초기화
        binding.fragmentContainerSearch.visibility = View.GONE
        binding.fragmentContainerKeep.visibility = View.GONE

        // 이미지 검색 버튼 클릭 시 이미지 검색 프래그먼트를 보이기
        binding.btnSearch.setOnClickListener {

            // 마지막 검색어를 검색창 입력 필드에 설정
            setLastSearchToSearchField(this, binding.etSearch)

            // 검색 버튼 클릭 이벤트 처리
            val searchQuery = binding.etSearch.text.toString()
            onSaveSearchClicked(this, searchQuery)

            binding.fragmentContainerSearch.visibility = View.GONE

            binding.btnExecuteSearch.setOnClickListener {
                // 키보드 숨기기
                hideKeyboard(it)

                // 검색어 가져오기
                val searchQuery = binding.etSearch.text.toString()

                // 이미지 검색 실행
                viewModel.searchImages(
                    apiKey = "d7dad5f8832c904973babb0a21d079ab",
                    query = searchQuery,
                    sort = "accuracy",
                    page = 1,
                    size = 80
                )
            }

            setUpImageClickListener()
            removeSelectedImages()

            val dateTime by lazy { }
            formatDateTime(dateTime)

            // 이미지 데이터를 관찰하여 UI를 업데이트
            viewModel.searchResults.observe(this, Observer { images ->
                SearchFragment.submitList(images)
            })
        }

        // 내 보관함 버튼 클릭 시 내 보관함 프래그먼트를 보이기
        binding.btnKeep.setOnClickListener {
            binding.fragmentContainerKeep.visibility = View.VISIBLE

            // todo: 보관된 이미지를 선택하면 해당 이미지를 제거하는 리스너 설정
        }
    }

    // 마지막 검색어를 SharedPreferences에 저장하는 함수
    private fun saveLastSearch(context: Context, searchQuery: String) {
        val sharedPreferences =
            context.getSharedPreferences(Companion.PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Companion.LAST_SEARCH_KEY, searchQuery)
        editor.apply()
    }

    // SharedPreferences에서 마지막 검색어를 불러오는 함수
    private fun getLastSearch(context: Context): String? {
        val sharedPreferences =
            context.getSharedPreferences(Companion.PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Companion.LAST_SEARCH_KEY, null)
    }

    // 앱을 재시작할 때 마지막 검색어를 검색창 입력 필드에 설정하는 함수
    private fun setLastSearchToSearchField(context: Context, searchEditText: EditText) {
        val lastSearch = getLastSearch(context)
        if (!lastSearch.isNullOrEmpty()) {
            searchEditText.setText(lastSearch)
            searchEditText.setSelection(lastSearch.length)
        }
    }

    // 검색 버튼 클릭 등의 이벤트에서 호출하여 마지막 검색어를 저장하는 함수
    private fun onSaveSearchClicked(context: Context, searchQuery: String) {
        saveLastSearch(context, searchQuery)
    }

    // 이미지를 클릭했을 때 호출되는 메서드
    private fun setUpImageClickListener() {
        val imageView = findViewById<ImageView>(R.id.iv_thumbnail)
        imageView.setOnClickListener {
            val imageUrl = "https://dapi.kakao.com"
            if (selectedImages.contains(imageUrl)) {
                selectedImages.remove(imageUrl)
            } else {
                selectedImages.add(imageUrl)
            }
        }
    }

    // 선택된 이미지를 제거하는 버튼 클릭 시 호출되는 메서드
    private fun removeSelectedImages() {
        selectedImages.forEach { imageUrl ->
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            val adapter = recyclerView.adapter
            remove(imageUrl)
        }
        selectedImages.clear() // 선택된 이미지 목록 초기화
    }

    private fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun formatDateTime(dateTime: Unit): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(dateTime)
    }

    object ImageStorage {

        private const val PREFS_NAME = "image_prefs"
        private const val KEY_IMAGE = "image"

        // 이미지를 Base64 문자열로 인코딩하여 저장
        fun saveImage(context: Context, bitmap: Bitmap) {
            val editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val imageBytes = byteArrayOutputStream.toByteArray()
            val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
            editor.putString(KEY_IMAGE, imageString)
            editor.apply()
        }

        // 저장된 Base64 문자열을 디코딩하여 이미지를 불러옴
        fun loadImage(context: Context): Bitmap? {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val imageString = prefs.getString(KEY_IMAGE, null)
            if (imageString != null) {
                val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
                return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            }
            return null
        }
    }

    companion object {
        // SharedPreferences 파일 이름
        private const val PREFS_NAME = "SearchPrefs"

        // 마지막 검색어를 저장하는 키
        private const val LAST_SEARCH_KEY = "last_search"
    }
}
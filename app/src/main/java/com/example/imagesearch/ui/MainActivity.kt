package com.example.imagesearch.ui

import android.content.ContentValues.TAG
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
import android.util.Log
import java.io.ByteArrayOutputStream
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.data.Repository
import com.example.imagesearch.network.RetrofitClient
import java.text.SimpleDateFormat
import java.util.Date
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
                // 검색 실행하고 네트워크 요청 보내기
                val searchQuery = binding.etSearch.text.toString()
                viewModel.getSearchImages(searchQuery)
            }

            viewModel.searchResultsLiveData(this, Observer {
                // 검색 결과를 받아서 UI에 표시하는 작업 수행
            })
        }

        setUpImageClickListener()
//        formatDateTime(dateTime: Date))
//        onImageClicked(imageUrl: String)
        removeSelectedImages()

        // 내 보관함 버튼 클릭 시 내 보관함 프래그먼트를 보이기
        binding.btnKeep.setOnClickListener {
            binding.fragmentContainerKeep.visibility = View.VISIBLE

            // 이미지 선택 버튼 클릭 시 선택된 이미지 제거
//            binding.btnRemoveSelectedImages.setOnClickListener {
//                removeSelectedImages()
//            }
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
            // 커서를 검색창 입력 필드의 끝으로 이동
            searchEditText.setSelection(lastSearch.length)
        }
    }

    // 검색 버튼 클릭 등의 이벤트에서 호출하여 마지막 검색어를 저장하는 함수
    private fun onSaveSearchClicked(context: Context, searchQuery: String) {
        saveLastSearch(context, searchQuery)
    }

    private fun setUpImageClickListener() {
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setOnClickListener {
            val imageUrl = "https://dapi.kakao.com"

            if (selectedImages.contains(imageUrl)) {
                selectedImages.remove(imageUrl)
            } else {
                selectedImages.add(imageUrl)
            }
        }
    }

    // 이미지를 클릭했을 때 호출되는 메서드
    private fun onImageClicked(imageUrl: String) {
        if (selectedImages.contains(imageUrl)) {
            selectedImages.remove(imageUrl)
        } else {
            selectedImages.add(imageUrl)
        }
    }

    // 선택된 이미지를 제거하는 버튼 클릭 시 호출되는 메서드
    private fun removeSelectedImages() {
        selectedImages.forEach { imageUrl ->
            // 이미지 제거 작업 수행

            // UI에서 선택한 이미지 제거 (예: RecyclerView에서 해당 아이템 제거)
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            val adapter = recyclerView.adapter
            remove(imageUrl)
        }

        selectedImages.clear() // 선택된 이미지 목록 초기화
    }

    private fun executeSearch(searchQuery: String) {
        val retrofitService = RetrofitClient.RetrofitInstance.retrofitService
        val response = retrofitService.getSearchImages(
            authorization = "KakaoAK ${RetrofitClient.RetrofitInstance.API_KEY}",
            query = searchQuery,
            sort = "accuracy",
            page = 1,
            size = 80 // 최대 80개의 결과를 요청
        )

        if (response.isSuccessful) {
            val itemList = response

            // 바인딩된 RecyclerView의 인스턴스를 가져와서 어댑터에 결과를 전달
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            val adapter = recyclerView.adapter as? SearchAdapter
            adapter?.itemList // itemList를 어댑터에 할당

        } else {
            // 네트워크 요청 실패 시 에러 처리
            Log.e(TAG, "Network request failed")
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // 특정 날짜와 시간을 포맷에 맞게 변환하는 함수
    private fun formatDateTime(dateTime: Date): String {
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
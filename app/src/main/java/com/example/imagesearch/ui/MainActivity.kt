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
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.network.RetrofitClient
import com.google.android.gms.ads.mediation.Adapter

class MainActivity : AppCompatActivity() {

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

        // 이미지 검색 프래그먼트와 내 보관함 프래그먼트를 숨김 상태로 초기화
        binding.fragmentContainerSearch.visibility = View.GONE
        binding.fragmentContainerKeep.visibility = View.GONE

        // 이미지 검색 버튼 클릭 시 이미지 검색 프래그먼트를 보이기
        binding.btnSearch.setOnClickListener {
            binding.fragmentContainerSearch.visibility = View.GONE

            binding.btnExecuteSearch.setOnClickListener {
                // 키보드 숨기기
                hideKeyboard(it)
                // 검색 실행하고 네트워크 요청 보내기
                var searchQuery = binding.etSearch.text.toString()
                executeSearch(searchQuery)
            }
        }

        // 내 보관함 버튼 클릭 시 내 보관함 프래그먼트를 보이기
        binding.btnKeep.setOnClickListener {
            binding.fragmentContainerKeep.visibility = View.VISIBLE

            // 이미지 선택 버튼 클릭 시 선택된 이미지 제거
//            binding.btnRemoveSelectedImages.setOnClickListener {
//                removeSelectedImages()
//            }
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
            // 이미지 제거 작업 수행 (예: 파일 삭제 등)

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
            size = 20
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
}
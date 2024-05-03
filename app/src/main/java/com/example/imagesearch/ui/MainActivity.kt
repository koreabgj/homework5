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
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.network.RetrofitInstance


class MainActivity : AppCompatActivity() {
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
                executeSearch(binding.etSearch.text.toString())
            }
        }

        // 내 보관함 버튼 클릭 시 내 보관함 프래그먼트를 보이기
        binding.btnKeep.setOnClickListener {
            binding.fragmentContainerKeep.visibility = View.VISIBLE
        }
    }

    private suspend fun executeSearch(searchQuery: String) {
        val retrofitService = RetrofitInstance.retrofitService
        val response = retrofitService.getSearchImages(
            Authorization = "KakaoAK ${RetrofitInstance.API_KEY}",
            query = searchQuery,
            sort = "accuracy",
            page = 1,
            size = 20
        )

        if (response.isSuccessful) {
            val imageResponse = response.body()
            val itemList = imageResponse?.ImageDocuments

            // 바인딩된 RecyclerView의 인스턴스를 가져와서 어댑터에 결과를 전달
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            val adapter = recyclerView.adapter as? SearchAdapter
            SearchAdapter.submitList()
        } else {
            // 네트워크 요청 실패 시 에러 처리
            Log.e(TAG, "Network request failed: ${response.message()}")
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
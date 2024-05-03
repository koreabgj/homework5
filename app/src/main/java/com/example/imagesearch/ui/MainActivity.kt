package com.example.imagesearch.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imagesearch.R
import com.example.imagesearch.databinding.ActivityMainBinding

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

            binding.etSearch
            binding.btnExecuteSearch.setOnClickListener {
                // etSearch에 들어간 내용을 검색 실행
            }
        }

        // 내 보관함 버튼 클릭 시 내 보관함 프래그먼트를 보이기
        binding.btnKeep.setOnClickListener {
            binding.fragmentContainerKeep.visibility = View.VISIBLE
        }

    }
}
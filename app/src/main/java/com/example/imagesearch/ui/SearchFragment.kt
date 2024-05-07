package com.example.imagesearch.ui

import SearchAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.R
import com.example.imagesearch.data.ImageDocuments

class SearchFragment : Fragment() {

    private val imageUrls = listOf(
        "https://dapi.kakao.com"
    )

    companion object {
        const val IMAGE_URLS_KEY = "https://dapi.kakao.com"
    }

    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val bundle = Bundle().apply {
            putStringArrayList(IMAGE_URLS_KEY, ArrayList(imageUrls))
        }

        val receiveFragment = KeepFragment()
        receiveFragment.arguments = bundle

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val adapter = SearchAdapter(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(imageUrl: String) {
                // 아이템 클릭 시 처리할 로직을 작성
            }
        })

        recyclerView.adapter = adapter

        return view
    }

    // 이미지 데이터를 관찰하여 UI를 업데이트합니다.
//            searchAdapter.submitList(images)
}
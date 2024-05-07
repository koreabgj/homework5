package com.example.imagesearch.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.R

class KeepFragment : Fragment() {

    private lateinit var keepFragmentImageUrls: List<String>

    private val imageUrlList = mutableListOf(
        "https://dapi.kakao.com"
    )

    companion object {
        const val IMAGE_URLS_KEY = "https://dapi.kakao.com"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val imageUrls = arguments?.getStringArrayList(IMAGE_URLS_KEY)

        if (imageUrls != null) {
            keepFragmentImageUrls = imageUrls
        }

        val view = inflater.inflate(R.layout.fragment_keep, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val adapter = KeepAdapter(imageUrlList, object : KeepAdapter.OnItemClickListener {
            override fun onItemClick(imageUrl: String) {
                // 클릭한 이미지의 위치를 찾아서 삭제
                val position = imageUrlList.indexOf(imageUrl)
                if (position != -1) {
                    imageUrlList.removeAt(position)
                }
            }
        })
        recyclerView.adapter = adapter

        return view
    }
}
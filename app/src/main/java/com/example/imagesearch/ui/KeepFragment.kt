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

    private var thumbnailUrlList = mutableListOf<String>()

    companion object {
        const val THUMBNAIL_URLS_KEY = "thumbnail_urls"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val thumbnailUrls = arguments?.getStringArrayList(THUMBNAIL_URLS_KEY)

        if (thumbnailUrls != null) {
            thumbnailUrlList = thumbnailUrls
        }

        val view = inflater.inflate(R.layout.fragment_keep, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val adapter = KeepAdapter(thumbnailUrlList, object : KeepAdapter.OnItemClickListener {
            override fun onItemClick(thumbnailUrl: String) {
                // 클릭한 이미지 위치를 찾아서 삭제
                val position = thumbnailUrlList.indexOf(thumbnailUrl)
                if (position != -1) {
                    thumbnailUrlList.removeAt(position)
                }
            }
        })

        recyclerView.adapter = adapter

        adapter.submitList(thumbnailUrlList)

        return view
    }
}
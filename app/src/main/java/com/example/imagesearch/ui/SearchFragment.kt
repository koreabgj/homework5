package com.example.imagesearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.R
import com.example.imagesearch.data.SearchResponse

class SearchFragment : Fragment() {

    private val thumbnailUrls = listOf(
        "https://dapi.kakao.com"
    )

    companion object {
        const val THUMBNAIL_URLS_KEY = "https://dapi.kakao.com"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        Bundle().apply {
            putStringArrayList(THUMBNAIL_URLS_KEY, ArrayList(thumbnailUrls))
        }

        fun navigateToKeepFragment(thumbnailUrl: String) {
            (requireActivity() as MainActivity).navigateToKeepFragment(thumbnailUrl)
        }

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        // 클릭한 URL을 KeepFragment로 전달
        val adapter = SearchAdapter(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(thumbnailUrl: String, position: Int) {
                navigateToKeepFragment(thumbnailUrl)
            }
        })

        recyclerView.adapter = adapter

        return view
    }
}
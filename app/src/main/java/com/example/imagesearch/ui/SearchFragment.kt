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

    private val imageUrls = listOf(
        "https://dapi.kakao.com"
    )

    companion object {
        const val IMAGE_URLS_KEY = "https://dapi.kakao.com"

        fun submitList(images: List<SearchResponse>?) {
            submitList(images)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        Bundle().apply {
            putStringArrayList(IMAGE_URLS_KEY, ArrayList(imageUrls))
        }

        fun navigateToKeepFragment(imageUrl: String) {
            (requireActivity() as MainActivity).navigateToKeepFragment(imageUrl)
        }

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        // 클릭한 이미지의 URL을 KeepFragment로 전달
        val adapter = SearchAdapter(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(imageUrl: String, position: Int) {
                navigateToKeepFragment(imageUrl)
            }
        })

        recyclerView.adapter = adapter

        return view
    }
}
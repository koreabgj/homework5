package com.example.imagesearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.R

class SearchFragment : Fragment() {

    private val thumbnailUrls = ArrayList<String>()

    companion object {
        const val THUMBNAIL_URLS_KEY = "thumbnail_urls"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // onCreateView 메서드에서 번들을 생성하고 프래그먼트의 인수로 설정
        arguments?.getStringArrayList(THUMBNAIL_URLS_KEY)?.let { thumbnailUrls.addAll(it) }

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

        adapter.submitList(thumbnailUrls)

        return view
    }

    fun navigateToKeepFragment(thumbnailUrl: String) {
        (requireActivity() as MainActivity).navigateToKeepFragment(thumbnailUrl)
    }
}
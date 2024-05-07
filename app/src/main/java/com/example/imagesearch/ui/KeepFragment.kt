package com.example.imagesearch.ui

import KeepAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.R

class KeepFragment : Fragment() {
    private val keepFragmentImageUrls = listOf(
        "https://dapi.kakao.com"
    )
    companion object {
        const val IMAGE_URLS_KEY = "https://dapi.kakao.com"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val imageUrls = arguments?.getStringArrayList(SearchFragment.IMAGE_URLS_KEY)

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val adapter = KeepAdapter(keepFragmentImageUrls)
        recyclerView.adapter = adapter

        return view
    }
}
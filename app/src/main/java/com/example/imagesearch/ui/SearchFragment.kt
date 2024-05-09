package com.example.imagesearch.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var viewModel: MainViewModel

    companion object {
        const val THUMBNAIL_URLS_KEY = "thumbnail_urls"
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        adapter = SearchAdapter(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(thumbnailUrl: String, position: Int) {
                // 이미지 클릭시 보관함으로 이동
                navigateToKeepFragment(thumbnailUrl)
            }
        })

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        // 썸네일 이미지 리스트 설정
        val thumbnailUrls = arguments?.getStringArrayList(THUMBNAIL_URLS_KEY)
        thumbnailUrls?.let {
            viewModel.setThumbnailUrls(it)
        }

        viewModel._thumbnailUrls.observe(viewLifecycleOwner, Observer {
            adapter.thumbnailUrls
            adapter.notifyDataSetChanged()
        })

        return binding.root
    }

    fun navigateToKeepFragment(thumbnailUrl: String) {
        (requireActivity() as MainActivity).navigateToKeepFragment(thumbnailUrl)
    }
}
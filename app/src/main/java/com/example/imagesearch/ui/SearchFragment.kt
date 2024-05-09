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
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SearchAdapter

    companion object {
        const val THUMBNAIL_URLS_KEY = "thumbnail_urls"
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        adapter = SearchAdapter(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(thumbnailUrl: String, position: Int) {
                navigateToKeepFragment(thumbnailUrl)
            }
        })

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

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

    private fun navigateToKeepFragment(thumbnailUrl: String) {
        (requireActivity() as MainActivity).navigateToKeepFragment(thumbnailUrl)
    }
}
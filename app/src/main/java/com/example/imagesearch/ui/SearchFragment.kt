package com.example.imagesearch.ui

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
    private val thumbnailUrls = ArrayList<String>()
    private lateinit var viewModel: MainViewModel

    companion object {
        const val THUMBNAIL_URLS_KEY = "thumbnail_urls"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        arguments?.getStringArrayList(THUMBNAIL_URLS_KEY)?.let { thumbnailUrls.addAll(it) }

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val adapter = SearchAdapter(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(thumbnailUrl: String, position: Int) {
                navigateToKeepFragment(thumbnailUrl)
            }
        })

        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.searchResults.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        return binding.root
    }

    private fun navigateToKeepFragment(thumbnailUrl: String) {
        (requireActivity() as MainActivity).navigateToKeepFragment(thumbnailUrl)
    }
}
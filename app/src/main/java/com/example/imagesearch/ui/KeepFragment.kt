package com.example.imagesearch.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.databinding.FragmentKeepBinding

class KeepFragment : Fragment() {

    private lateinit var binding: FragmentKeepBinding
    private lateinit var adapter: KeepAdapter
    private var thumbnailUrlList = mutableListOf<String>()
    private lateinit var viewModel: MainViewModel

    companion object {
        const val THUMBNAIL_URLS_KEY = "thumbnail_urls"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentKeepBinding.inflate(inflater, container, false)

        val thumbnailUrls = arguments?.getStringArrayList(THUMBNAIL_URLS_KEY)
        if (thumbnailUrls != null) {
            thumbnailUrlList.addAll(thumbnailUrls)
        }

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter = KeepAdapter(thumbnailUrlList, object : KeepAdapter.OnItemClickListener {
            override fun onItemClick(thumbnailUrl: String) {
                // 클릭한 이미지 위치를 찾아서 삭제
                val position = thumbnailUrlList.indexOf(thumbnailUrl)
                if (position != -1) {
                    thumbnailUrlList.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            }
        })

        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.selectedImages.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        return binding.root
    }
}
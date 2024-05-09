package com.example.imagesearch.ui

import android.annotation.SuppressLint
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
    private lateinit var viewModel: MainViewModel

    companion object {
        const val THUMBNAIL_URLS_KEY = "thumbnail_urls"
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentKeepBinding.inflate(inflater, container, false)

        val thumbnailUrls = arguments?.getStringArrayList(THUMBNAIL_URLS_KEY)
        val thumbnailUrlList = mutableListOf<String>()
        thumbnailUrls?.let {
            thumbnailUrlList.addAll(it)
        }

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.thumbnailUrlList.observe(viewLifecycleOwner, Observer { list ->
            thumbnailUrlList.clear()
            thumbnailUrlList.addAll(list)
            adapter.notifyDataSetChanged()
        })

        adapter = KeepAdapter(thumbnailUrlList, object : KeepAdapter.OnItemClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClick(thumbnailUrl: String) {
                // 클릭한 이미지 위치를 찾아서 삭제
                val position = thumbnailUrlList.indexOf(thumbnailUrl)
                if (position != -1) {
                    thumbnailUrlList.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            }
        })

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        return binding.root
    }
}
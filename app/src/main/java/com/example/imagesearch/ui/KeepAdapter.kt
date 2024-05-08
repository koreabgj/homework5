package com.example.imagesearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.databinding.ItemLayoutBinding

class KeepAdapter(
    private val thumbnailUrlList: List<String>,
    private val itemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<KeepAdapter.ImageViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(thumbnailUrl: String)
    }

    inner class ImageViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val thumbnailUrl = thumbnailUrlList[adapterPosition] // 아이템의 위치에 맞는 imageUrl
                itemClickListener.onItemClick(thumbnailUrl)
            }
        }

        fun bind(imageUrl: String) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .into(binding.ivThumbnail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = thumbnailUrlList[position]
        holder.bind(imageUrl)
    }

    override fun getItemCount(): Int {
        return thumbnailUrlList.size
    }
}
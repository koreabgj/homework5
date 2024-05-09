package com.example.imagesearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.databinding.ItemLayoutBinding

class KeepAdapter(
    private val thumbnailUrlList: List<String>,
    param: OnItemClickListener,
) : RecyclerView.Adapter<KeepAdapter.ImageViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(thumbnailUrl: String)
    }

    private var itemClickListener: OnItemClickListener? = null

    inner class ImageViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(thumbnailUrlList[position])
                }
            }
        }

        fun bind(imageUrl: String) {
            Glide.with(binding.ivThumbnail.context)
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
        holder.bind(thumbnailUrlList[position])
    }

    override fun getItemCount(): Int {
        return thumbnailUrlList.size
    }
}
package com.example.imagesearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.R
import com.example.imagesearch.data.ImageDocuments
import com.example.imagesearch.databinding.ItemLayoutBinding

class SearchAdapter(
    private val itemClickListener: OnItemClickListener,
    private var thumbnailUrls: List<ImageDocuments> = emptyList(),
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(thumbnailUrl: String, position: Int)
    }

    inner class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var isLiked = false

        fun bind(item: ImageDocuments) {
            binding.apply {
                // 이미지 썸네일
                Glide.with(itemView.context)
                    .load(item.thumbnailUrl)
                    .into(ivThumbnail)

                // 이미지 소스
                tvSite.text = item.displaySiteName

                // 이미지 업로드 날짜 및 시간
                tvDatetime.text = item.dateTime.toString()

                binding.ivLike.setOnClickListener {
                    // 좋아요 상태를 반전
                    isLiked = !isLiked

                    // 좋아요 상태에 따라 이미지 변경
                    val imageResource =
                        if (isLiked) R.drawable.img_favorite else R.drawable.img_empty_favorite
                    binding.ivLike.setImageResource(imageResource)

                    // 클릭한 아이템의 위치를 전달
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(thumbnailUrl = "", position)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = thumbnailUrls[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return thumbnailUrls.size
    }

    fun submitList(images: ArrayList<String>) {
        thumbnailUrls
        notifyDataSetChanged()
    }
}
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
    private var itemList: List<ImageDocuments> = emptyList(),
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(imageUrl: String, position: Int)
    }

    inner class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

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

                // 아이템 클릭 시 이벤트 처리
                binding.root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(imageUrl = "", position)
                    }
                }

//                // 좋아요 상태에 따라 UI 업데이트
//                if (image.isLiked) {
//                    binding.ivLike.setImageResource(R.drawable.ic_liked)
//                } else {
//                    binding.ivLike.setImageResource(R.drawable.ic_unliked)
//                }
//
//                // 좋아요 아이콘 클릭 시 이벤트 처리
//                binding.ivLike.setOnClickListener {
//                    val position = adapterPosition
//                    if (position != RecyclerView.NO_POSITION) {
//                        itemClickListener.onItemClick(imageUrl = "", position)
//                    }
//                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
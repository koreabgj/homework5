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
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(imageUrl: String, position: Int)
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

                // 아이템 클릭 시 이벤트 처리
                binding.root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(imageUrl = "", position)
                    }
                }

                binding.ivLike.setOnClickListener {
                    if (isLiked) {
                        // 이미 좋아요된 상태이면 비어있는 하트로 변경
                        binding.ivLike.setImageResource(R.drawable.img_empty_favorite)
                    } else {
                        // 좋아요되지 않은 상태이면 채워진 하트로 변경
                        binding.ivLike.setImageResource(R.drawable.img_favorite)
                    }

                    // 좋아요 상태를 서버에 업데이트하고 UI에 반영
                    isLiked = !isLiked

                    // 클릭한 아이템의 위치를 전달
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(imageUrl = "", position)
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
        val currentItem = itemList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
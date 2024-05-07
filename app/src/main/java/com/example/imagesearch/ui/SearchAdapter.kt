package com.example.imagesearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.databinding.ItemLayoutBinding
import com.example.imagesearch.data.ImageDocuments
import com.bumptech.glide.Glide

class SearchAdapter(private val onClick: List<String>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var itemList: List<ImageDocuments> = emptyList()

    class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageDocuments) {
            binding.apply {
                // 이미지 썸네일 로드
                Glide.with(itemView.context)
                    .load(item.thumbnailUrl)
                    .into(ivThumbnail)

                // 이미지 소스 (display_sitename)
                tvSite.text = item.displaySiteName

                // 이미지 업로드 날짜 및 시간
                tvDatetime.text = item.dateTime.toString()

                // 아이템 클릭 이벤트 처리
                root.setOnClickListener {
                    // 클릭 이벤트 처리 코드 작성
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    companion object {
        fun submitList() {
            notifyDataSetChanged()
        }

        private fun notifyDataSetChanged() {
            notifyDataSetChanged()
        }
    }
}
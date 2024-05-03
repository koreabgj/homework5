package com.example.imagesearch.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.data.Image
import com.example.imagesearch.databinding.ItemLayoutBinding

class SearchAdapter(private val onClick: List<String>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var itemList: List<Image> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(inputList: List<Image>) {
        itemList = inputList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Image) {
            binding.apply {
                loadImage(item.imageUrl, ivItem) // 이미지의 URL을 전달하여 loadImage 함수 호출
                root.setOnClickListener { }
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

    // 이미지를 로드하고 ImageView에 설정하는 함수
    private fun loadImage(url: String, imageView: ImageView) {
        // 실제로는 네트워크 라이브러리를 사용하여 이미지를 가져와야 합니다.
        // 이미지를 가져오는 코드는 이곳에 작성합니다.
    }
}
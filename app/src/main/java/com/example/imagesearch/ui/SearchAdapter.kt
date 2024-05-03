package com.example.imagesearch.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.data.Image
import com.example.imagesearch.databinding.ItemLayoutBinding

// 생성자에 itemList를 넘기지 말고 아래 "submitList" 함수를 public으로 만들어서 외부에서 호출하도록 설정
class SearchAdapter(private val onClick: List<String>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var itemList: List<Image> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(inputList: List<Image>) {
        itemList = inputList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemLayoutBinding, private val onClick: List<String>) :
        RecyclerView.ViewHolder(binding.root) {
        private var item: Image? = null

        init {
            binding.root.setOnClickListener {
                item?.let {
                    onClick(it)
                }
            }
        }

        private fun onClick(it: Image) {

        }

        fun bind(item: Image) {
            this.item = item
//            binding.ivItem.setImageResource(item.imageResourceId)
//            binding.tvItem.text = item.item
//            binding.tvAddress.text = item.address
//            binding.tvPrice.text = item.getFormattedPrice()
//            binding.tvChatCount.text = item.chatCount.toString()
//            binding.tvLikeCount.text = item.likeCount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(item = currentItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
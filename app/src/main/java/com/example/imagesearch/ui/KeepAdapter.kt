import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.databinding.ItemLayoutBinding

class KeepAdapter(
    private val imageUrlList: List<String>
) : RecyclerView.Adapter<KeepAdapter.ImageViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(imageUrl: String)
    }

    inner class ImageViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            // 이미지 로드
            Glide.with(itemView.context)
                .load(imageUrl)
                .into(binding.ivThumbnail)

            // 이미지 클릭 시 이벤트 처리
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(imageUrl)
            }
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
        val imageUrl = imageUrlList[position]
        holder.bind(imageUrl)
    }

    override fun getItemCount(): Int {
        return imageUrlList.size
    }
}
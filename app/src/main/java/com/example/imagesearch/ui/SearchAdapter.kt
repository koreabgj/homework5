import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.data.ImageDocuments
import com.example.imagesearch.databinding.ItemLayoutBinding

class SearchAdapter(
    private val itemClickListener: OnItemClickListener, // 아이템 클릭 리스너 추가
    private var itemList: List<ImageDocuments> = emptyList()
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    // 아이템 클릭 리스너 인터페이스 정의
    interface OnItemClickListener {
        fun onItemClick(imageUrl: String)
    }

    inner class ViewHolder(private val binding: ItemLayoutBinding) :
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
                    val imageUrl = item.imageUrl
                    itemClickListener.onItemClick(imageUrl)
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
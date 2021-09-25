package net.mindzone.robroo.ui.main.share.listShare.writeMyBlog

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.mindzone.robroo.R
import net.mindzone.robroo.databinding.CellChooseImageBinding
import net.mindzone.robroo.databinding.CellInsertImageBlogBinding
import net.mindzone.robroo.ui.main.share.listShare.ShareViewModel

const val VIEW_TYPE_ONE = 1
const val VIEW_TYPE_TWO = 2
class InsertImageAdapter(val context: Context,
    private val viewModel: ShareViewModel, val onItemClick : ItemClick

) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = mutableListOf<Uri>()
    interface ItemClick{
        fun onClick()
    }
    fun submitList(list : MutableList<Uri>){
        this.list = list
        notifyDataSetChanged()
    }
    fun clearList() {
        list = arrayListOf()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CellInsertImageBlogBinding.inflate(layoutInflater, parent, false)
        val view2 = LayoutInflater.from(context).inflate(R.layout.cell_choose_image, parent, false)

        return if (viewType == VIEW_TYPE_ONE) {
            InsertImageHolder(binding)
        } else {
            ViewHolder2(view2)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ONE){
            val viewholder1 =holder as InsertImageHolder
            viewholder1.bind(viewModel)
        } else {
            val viewHolder2 = holder as ViewHolder2
            viewHolder2.bind()
        }
    }
//    override fun onBindViewHolder(holder: InsertImageHolder, position: Int) {
////        holder.binding.apply {
////            viewModel = this@InsertImageAdapter.viewModel
////        }
//        holder.bind(viewModel)
//    }
    override fun getItemCount(): Int {
        return 4
//        if (list.isNullOrEmpty()){
//            return 4
//        }else{
//            return list.size
//        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list.size <= position){
            VIEW_TYPE_TWO
        }else VIEW_TYPE_ONE
    }
    inner class ViewHolder2 (itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(){
            itemView.setOnClickListener {
                onItemClick.onClick()
            }
        }
    }
    inner class InsertImageHolder(val binding: CellInsertImageBlogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick.onClick()
            }
        }
        fun bind(
            shareViewModel: ShareViewModel,
//            url: String,
//            position:Int
        ) {
            binding.apply {
                this.viewModel = shareViewModel
                this.url = url

                if (!list.isNullOrEmpty() ){
                    var pos = list[position]
                    Glide.with(context).load(pos).error(R.drawable.bg_insert_image_blog).into(image)
                }
//                Glide.with(image).load(url).error(R.drawable.bg_insert_image_blog).into(image)
            }
        }
    }




}
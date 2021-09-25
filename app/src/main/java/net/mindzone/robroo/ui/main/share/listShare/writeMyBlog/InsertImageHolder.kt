package net.mindzone.robroo.ui.main.share.listShare.writeMyBlog

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.mindzone.robroo.R
import net.mindzone.robroo.databinding.CellInsertImageBlogBinding
import net.mindzone.robroo.ui.main.share.listShare.ShareViewModel

class InsertImageHolder(val binding: CellInsertImageBlogBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        shareViewModel: ShareViewModel,
        url: String,
        onSelect: (Int) -> Unit,
        position:Int
    ) {
        binding.apply {
            this.viewModel = shareViewModel
            this.url = url
            this.root.setOnClickListener {
                onSelect(position)
            }
            Glide.with(image).load(url).error(R.drawable.bg_insert_image_blog).into(image)
        }
    }
}
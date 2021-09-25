package net.mindzone.robroo.ui.main.share.listShare.listDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import net.mindzone.robroo.data.forumArticle.entity.comment.Reply
import net.mindzone.robroo.databinding.CellReplyCommentMenu4Binding
import net.mindzone.robroo.ui.main.share.listShare.ShareViewModel

class ReplyCommentAdapter(private val viewModel: ShareViewModel) :
    ListAdapter<Reply, ReplyCommentHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: ReplyCommentHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyCommentHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CellReplyCommentMenu4Binding.inflate(layoutInflater, parent, false)
        return ReplyCommentHolder(binding)
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Reply>() {
            override fun areItemsTheSame(oldItem: Reply, newItem: Reply) =
                oldItem.commentId == newItem.commentId
            override fun areContentsTheSame(oldItem: Reply, newItem: Reply) =
                oldItem == newItem
        }
    }
}

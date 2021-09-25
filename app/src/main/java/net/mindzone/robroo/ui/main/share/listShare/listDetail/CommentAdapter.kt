package net.mindzone.robroo.ui.main.share.listShare.listDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import net.mindzone.robroo.data.forumArticle.entity.comment.Comment
import net.mindzone.robroo.databinding.CellCommentMenu4Binding
import net.mindzone.robroo.ui.main.share.listShare.ShareViewModel

class CommentAdapter(private val viewModel: ShareViewModel) :
    ListAdapter<Comment, CommentHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: CommentHolder, position: Int, payloads: MutableList<Any>) {
        holder.bind(viewModel, getItem(position), payloads)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bind(viewModel, getItem(position), arrayListOf())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CellCommentMenu4Binding.inflate(layoutInflater, parent, false)
        val replyCommentAdapter = ReplyCommentAdapter(viewModel)
        binding.rvCommentReply.adapter = replyCommentAdapter
        return CommentHolder(binding, replyCommentAdapter)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment) =
                oldItem.commentId == newItem.commentId
            override fun areContentsTheSame(oldItem: Comment, newItem: Comment) =
                oldItem == newItem
            override fun getChangePayload(oldItem: Comment, newItem: Comment): Any {
                val set = mutableSetOf<String>()
                if (oldItem.likes != newItem.likes) set.add("likes")
                if (oldItem.replies != newItem.replies) set.add("replies")
                return set
            }
        }
    }

}

package net.mindzone.robroo.ui.main.share.listShare.listDetail

import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.R
import net.mindzone.robroo.data.forumArticle.entity.comment.Comment
import net.mindzone.robroo.databinding.CellCommentMenu4Binding
import net.mindzone.robroo.ui.main.share.listShare.ShareViewModel

class CommentHolder(
    val binding: CellCommentMenu4Binding,
    val replyCommentAdapter: ReplyCommentAdapter
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        shareViewModel: ShareViewModel,
        comment: Comment?,
        payloads: MutableList<Any>
    ) {
        binding.apply {
            if (payloads.isEmpty()) {
                this.viewModel = shareViewModel
                this.comment = comment
                replyCommentAdapter.submitList(comment?.replies)
            } else {
                val set = payloads.firstOrNull() as Set<*>?
                set?.forEach {
                    when (it) {
                        "likes" -> {
                            tvCommentLike.text = comment?.likes.toString()
                            if (comment?.isLiked == 0) imgViewCommentLike.setImageResource(R.drawable.ic_unlike)
                            else imgViewCommentLike.setImageResource(R.drawable.ic_like)
                        }
                        "replies" -> {
                            replyCommentAdapter.submitList(comment?.replies)
                            tvCountComment.text = comment?.replies?.size.toString()
                            etReplyComment.text?.clear()
                        }
                        else -> {
                            this.viewModel = shareViewModel
                            this.comment = comment
                            replyCommentAdapter.submitList(comment?.replies)
                        }
                    }
                }
            }
        }
    }
}
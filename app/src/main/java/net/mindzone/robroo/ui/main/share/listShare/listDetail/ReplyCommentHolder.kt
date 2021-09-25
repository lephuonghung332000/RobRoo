package net.mindzone.robroo.ui.main.share.listShare.listDetail

import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.data.forumArticle.entity.comment.Reply
import net.mindzone.robroo.databinding.CellReplyCommentMenu4Binding
import net.mindzone.robroo.ui.main.share.listShare.ShareViewModel

class ReplyCommentHolder(val binding: CellReplyCommentMenu4Binding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        shareViewModel: ShareViewModel,
        reply: Reply?
    ) {
        binding.apply {
            this.viewModel = shareViewModel
            this.reply = reply
        }
    }
}
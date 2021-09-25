package net.mindzone.robroo.ui.main.share.listShare

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import co.lujun.androidtagview.TagContainerLayout
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.forumArticle.entity.forumArticles.ForumArticles
import net.mindzone.robroo.databinding.CellListAllMenu4Binding
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

class ListAllViewHolder(val context: Context,val binding: CellListAllMenu4Binding, val lifecycleOwner: LifecycleOwner) :
    RecyclerView.ViewHolder(binding.root) {
    val recyclerView = binding.listTagview
    fun bind(
        shareViewModel: ShareViewModel,
        forumArticles: ForumArticles,
        onSelect: (ForumArticles) -> Unit
    ) {
        binding.apply {
            this.viewModel = shareViewModel
            this.forumArticles = forumArticles
            this.root.setOnClickListener {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,"forumArticles",0,this::class.qualifiedName)
                onSelect(forumArticles)
            }

        }
    }

}


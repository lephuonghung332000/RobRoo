package net.mindzone.robroo.ui.main.share.mainMenu4

import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.forumArticle.entity.forumList.ForumGroups
import net.mindzone.robroo.databinding.CellMenu4MainBinding
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

class Menu4ViewHolder(val binding: CellMenu4MainBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        menu4MainViewModel: Menu4MainViewModel,
        forumGroup: ForumGroups,
        onSelect: (ForumGroups) -> Unit
    ) {
        binding.apply {
            this.viewModel = menu4MainViewModel
            this.forumGroup = forumGroup
            this.root.setOnClickListener {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click, "ForumGroup" ,forumGroup.groupId.toInt(),forumGroup.title)
                onSelect(forumGroup)
            }
        }
    }
}
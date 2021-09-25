package net.mindzone.robroo.ui.main.share.mainMenu4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleSaveDraft
import net.mindzone.robroo.data.forumArticle.entity.forumList.ForumGroups
import net.mindzone.robroo.databinding.CellMenu4MainBinding

class Menu4MainAdapter(private val viewModel: Menu4MainViewModel, private val onSelect: (ForumGroups) -> Unit) :
    ListAdapter<ForumGroups, Menu4ViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: Menu4ViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position), onSelect)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Menu4ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CellMenu4MainBinding.inflate(layoutInflater, parent, false)
        return Menu4ViewHolder(binding)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ForumGroups>() {
            override fun areItemsTheSame(oldItem: ForumGroups, newItem: ForumGroups) = oldItem.groupId == newItem.groupId
            override fun areContentsTheSame(oldItem: ForumGroups, newItem: ForumGroups) = oldItem == newItem
        }
    }
}
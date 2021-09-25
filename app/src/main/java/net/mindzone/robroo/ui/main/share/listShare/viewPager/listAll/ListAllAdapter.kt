package net.mindzone.robroo.ui.main.share.listShare


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.R
import net.mindzone.robroo.data.forumArticle.entity.forumArticles.ForumArticles
import net.mindzone.robroo.data.subMenu.entity.SubMenu
import net.mindzone.robroo.databinding.CellListAllMenu4Binding
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.share.listShare.subListInCellListAllMenu4.TagListAdapter

class ListAllAdapter(
    val context: Context,
    private val viewModel: ShareViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val onSelect: (ForumArticles) -> Unit

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private var list: MutableList<ForumArticles?> = mutableListOf()

    fun addList(list: MutableList<ForumArticles?>) {
        this.list.addAll(list.toMutableList())
        notifyDataSetChanged()
    }
    fun submitList(list: MutableList<ForumArticles?>) {
        this.list = list
        notifyDataSetChanged()
    }
    fun clearList() {
        list = arrayListOf()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position] == null) {
            return VIEW_TYPE_LOADING
        } else {
            return  VIEW_TYPE_ITEM
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        if (viewType == VIEW_TYPE_ITEM) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CellListAllMenu4Binding.inflate(layoutInflater, parent, false)
            binding.lifecycleOwner = lifecycleOwner
            return  ListAllViewHolder(context, binding, lifecycleOwner)
        } else {
            view = LayoutInflater.from(context).inflate(
                R.layout.item_loading,
                parent,
                false
            )
            return  ProgressViewHolder(view)
        }
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding = CellListAllMenu4Binding.inflate(layoutInflater, parent, false)
//        binding.lifecycleOwner = lifecycleOwner
//        return ListAllViewHolder(context, binding, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {



        if(holder is ListAllViewHolder) {
            var items = list[position]!!.tags
            var itemListAdapter = TagListAdapter(context, items)
            holder.recyclerView.setHasFixedSize(true)
            holder.recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            holder.recyclerView.adapter = itemListAdapter
            holder.bind(viewModel, list[position]!!, onSelect)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun addLoadingView() {
        list.add(null)
        notifyItemInserted(list.size)
    }
    fun removeLoadingView() {
        if (list.size != 0) {
            list.removeAt(list.size - 1)
            notifyItemRemoved(list.size - 1)
        }
    }
    fun filterList(filterdList: MutableList<ForumArticles?>){
        list = filterdList
        notifyDataSetChanged()
    }

    inner class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

}


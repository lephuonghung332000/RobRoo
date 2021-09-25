package net.mindzone.robroo.ui.main.home.subMenuChoose

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.data.subMenu.entity.SubMenu
import net.mindzone.robroo.databinding.CellSubMenuChooseBinding
import net.mindzone.robroo.ui.main.home.touchhelper.ItemTouchHelperAdapter
import net.mindzone.robroo.ui.main.home.touchhelper.ItemTouchHelperViewHolder
import net.mindzone.robroo.ui.main.home.touchhelper.OnStartDragListener
import java.util.*

class SubMenuChooseAdapter(val viewModel: SubMenuChooseViewModel, var dragListener: OnStartDragListener) :
    RecyclerView.Adapter<SubMenuChooseAdapter.ViewHolder>() , ItemTouchHelperAdapter {
    private var list: MutableList<SubMenu> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CellSubMenuChooseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            odd = position % 2 == 0
            item = list[position]
            viewModel = this@SubMenuChooseAdapter.viewModel

        }
        holder.itemView.setOnLongClickListener {
            dragListener.onStartDrag(holder)
            false
        }


//        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun getItemId(position: Int): Long {
        return list[position].uniqueid.hashCode().toLong()
    }

    fun submitList(subMenuList: MutableList<SubMenu>) {
//        val oldList = list
//        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
//            ItemDiffCallBack(
//                oldList, subMenuList
//            )
//        )
        list = subMenuList
//        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

//    class ItemDiffCallBack(var oldList: List<SubMenu>, var newList: List<SubMenu>) :
//        DiffUtil.Callback() {
//        override fun getOldListSize(): Int {
//            return oldList.size
//        }
//
//        override fun getNewListSize(): Int {
//            return newList.size
//        }
//
//        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//            return (oldList[oldItemPosition].selected == newList[oldItemPosition].selected)
//        }
//
//        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//            return oldList[oldItemPosition] == newList[newItemPosition]
//        }
//
//    }



    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(list, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }
    override fun onItemDismiss(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }


    inner class ViewHolder(val binding: CellSubMenuChooseBinding) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {
        override fun onItemSelected() {
            // TODO("Not yet implemented")
        }

        override fun onItemClear() {
            viewModel.updatePosition(list)
            list.forEach {
                viewModel.updateSubMenu(it)

            }
        }

    }
}
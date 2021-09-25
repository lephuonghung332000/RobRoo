package net.mindzone.robroo.ui.main.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.subMenu.entity.SubMenu
import net.mindzone.robroo.ui.main.home.touchhelper.ItemTouchHelperAdapter
import net.mindzone.robroo.ui.main.home.touchhelper.ItemTouchHelperViewHolder
import net.mindzone.robroo.ui.main.home.touchhelper.OnStartDragListener
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager
import java.util.*


class SubMenuAdapter(val viewModel: HomeViewModel,
    val context: Context,
    val iClickListener: IClickListener
) : RecyclerView.Adapter<SubMenuAdapter.ViewHolder>(), ItemTouchHelperAdapter {
    private var list: MutableList<SubMenu> = mutableListOf()

    interface IClickListener {
        fun itemClick(subMenu: SubMenu)

    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(this.context).inflate(R.layout.cell_submenu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBind(list[position])

    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun getItemId(position: Int): Long {
        return list[position].uniqueid.hashCode().toLong()
    }
    fun submitList(subMenuList: MutableList<SubMenu>) {
        list = subMenuList
    }


    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {

        Collections.swap(list, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)

        return true
    }


    override fun onItemDismiss(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)


        Log.d("itemdissis : ", position.toString())
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
         {
        private val imgIcon = view.findViewById<ImageView>(R.id.imgIcon)
        private val tvTitle = view.findViewById<TextView>(R.id.tvCategory)

        init {


            itemView.setOnClickListener {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,tvTitle.text.toString())
                iClickListener.itemClick(list[layoutPosition])

            }

        }

        fun onBind(subMenuChecked: SubMenu) {
            imgIcon.setImageResource(subMenuChecked.image2)
            tvTitle.text = subMenuChecked.titleth
        }



    }


    private fun updatePosition(items: List<SubMenu>)  {
        items.forEachIndexed { index, item ->
            item.position = index + 1

        }

    }
    fun filterList(filterdList: MutableList<SubMenu>){
        list = filterdList
        notifyDataSetChanged()
    }

}
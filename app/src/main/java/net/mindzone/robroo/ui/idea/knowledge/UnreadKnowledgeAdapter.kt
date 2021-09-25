package net.mindzone.robroo.ui.main.idea.knowledge

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.menu.entity.listNewUnderTopMostLevel.Items
import net.mindzone.robroo.data.news.entity.ResponseData
import net.mindzone.robroo.ui.main.idea.knowledge.detail.ListDetailMenu5Fragment
import net.mindzone.robroo.ui.main.share.listShare.subListInCellListAllMenu4.TagListAdapter
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

class UnreadKnowledgeAdapter(private val responseData: ResponseData,
                           val context: Context ):
    RecyclerView.Adapter<UnreadKnowledgeAdapter.MyViewHolder>() {
    var listFilter=responseData.items.filter { it.readstatus==0 }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_flash_knowledge_menu5, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(listFilter[position])
        val items = listFilter[position].tags
        val itemListAdapter = TagListAdapter(context, items)
        holder.listTagview.setHasFixedSize(true)
        holder.listTagview.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        holder.listTagview.adapter = itemListAdapter
    }

    override fun getItemCount(): Int {
        return listFilter.size
    }
    inner class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var txtTitleItem: TextView = view.findViewById(R.id.tvTitleItemMenu5)
        var tvDateTime: TextView = view.findViewById(R.id.tvDateTime)
        var textContentMenu5: TextView = view.findViewById(R.id.textContentMenu5)
        var tvViewStats: TextView = view.findViewById(R.id.tvViewStats)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val listTagview = view.findViewById<RecyclerView>(R.id.listTag)
        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                sendData(listFilter[pos],responseData.titleth)
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,responseData.groupid,responseData.titleth)
            }
            textContentMenu5.setOnClickListener{
                val pos = adapterPosition
                sendData(listFilter[pos],responseData.titleth)
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,responseData.groupid,responseData.titleth)
            }
        }
        private fun sendData (items: Items, title :String){
            val fragment = ListDetailMenu5Fragment()
            val bundle = Bundle()
            bundle.putString("title8",title)
            bundle.putSerializable("items", items)
            fragment.arguments = bundle
            newFragment(fragment, context)

        }
        fun onBind(string: Items) {
            txtTitleItem.text = string.titleth
            textContentMenu5.text = string.introth
            tvDateTime.text = string.contentdatetime
            tvViewStats.text = string.viewstats.toString()
            Glide.with(context).load(string.imageth).into(imageView)

        }
        private fun newFragment(fragment: Fragment, context: Context ){
            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right)
            transaction
                .replace(R.id.frameMain, fragment)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.addToBackStack(null)
            transaction.commit()


        }

    }

}
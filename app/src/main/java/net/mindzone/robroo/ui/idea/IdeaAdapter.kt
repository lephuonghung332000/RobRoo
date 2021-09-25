package net.mindzone.robroo.ui.main.idea

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.menu.entity.listNewUnderTopMostLevel.ResponseData
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

class IdeaAdapter(private val list: List<ResponseData>,
                  private val context: Context,
                  var itemListener: (content: String, group_id: Int) -> Unit) :
    RecyclerView.Adapter<IdeaAdapter.MyViewHolder>() {
    inner class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var txtMenuIdea: TextView = view.findViewById(R.id.txtMenuIdea)
        fun onBind(string: ResponseData) {
            txtMenuIdea.text=string.titleth
        }
        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                itemListener(txtMenuIdea.text.toString(), list[pos].groupid)
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,list[pos].groupid,txtMenuIdea.text.toString())
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_menu5_list,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
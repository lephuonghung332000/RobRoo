package net.mindzone.robroo.ui.main.information

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager


class InformationAdapter(
    private val list: MutableList<String>,
    private val context: Context,
    var itemListener: ((String) -> Unit)
) :
    RecyclerView.Adapter<InformationAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu2_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var txtMenuProduct: TextView = view.findViewById(R.id.txtMenuProduct)
        fun onBind(string: String) {
            txtMenuProduct.setText(string)
        }

        init {
            itemView.setOnClickListener {
                itemListener.invoke(list[position])
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,txtMenuProduct.text.toString())
            }
        }
    }

}
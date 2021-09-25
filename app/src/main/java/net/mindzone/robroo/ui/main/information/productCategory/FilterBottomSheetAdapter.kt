package net.mindzone.robroo.ui.main.information.productCategory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.productgroupListproductmodel.entity.ResponseData
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

class FilterBottomSheetAdapter(list: List<ResponseData>,
                               context: Context,
                               var itemListener: ((String) -> Unit)
                             ) :
        RecyclerView.Adapter<FilterBottomSheetAdapter.MyViewHolder>() {
    private  var listFilter= mutableSetOf<String>()
    init{
        listFilter.add(context.getString(R.string.list_all))
        for(i in list.indices){
           listFilter.add(context.getString(R.string.category)+" "+list[i].modelprefix)
        }
    }
    inner class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        var tvProductCategoryFilter: TextView = view.findViewById(R.id.tvProductCategoryFilter)
        fun onBind(string: String) {
            tvProductCategoryFilter.text = string
        }

        init {
            itemView.setOnClickListener {
                itemListener(tvProductCategoryFilter.text.toString())
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_filter_bottom_sheet_menu2, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(listFilter.elementAt(position))
    }

    override fun getItemCount(): Int {
        return listFilter.size
    }


}

package net.mindzone.robroo.ui.main.information.productCategory

 import android.content.Context
 import android.os.Build
 import android.view.LayoutInflater
 import android.view.View
 import android.view.ViewGroup
 import android.widget.TextView
 import androidx.constraintlayout.widget.ConstraintLayout
 import androidx.recyclerview.widget.RecyclerView
 import net.mindzone.robroo.R
 import net.mindzone.robroo.RobRooApplication
 import net.mindzone.robroo.data.productgroupListproductmodel.entity.ResponseData
 import net.mindzone.robroo.utils.AuditType
 import net.mindzone.sampleaudit.AuditManager

class ProductCategoryAdapter(private var list: List<ResponseData>,
                             private val context: Context,
                             var itemListener: (model_id: String) -> Unit) :
    RecyclerView.Adapter<ProductCategoryAdapter.MyViewHolder>() {
     var listSearch: List<ResponseData> = list
    inner class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var constrainLayout: ConstraintLayout = view.findViewById(R.id.childProductInfo)
        var txtProductCategory: TextView = view.findViewById(R.id.txtProductInfo)
        fun onBind(string: ResponseData) {
            txtProductCategory.text = string.modelcode
        }
        init {
            itemView.setOnClickListener {
                itemListener(txtProductCategory.text.toString())
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,txtProductCategory.text.toString())
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_product_info, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(listSearch[position])
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (position % 2 == 0) {
                holder.constrainLayout.setBackgroundColor(context.getColor(R.color.white))
            } else {
                holder.constrainLayout.setBackgroundColor(context.getColor(R.color.color_F5F5F5))
            }
        }
    }

    override fun getItemCount(): Int {
        return listSearch.size
    }
    fun filterList(checkEmpty: String) {
        when (checkEmpty) {
            "" -> {
                listSearch = list
            }
            else -> {
                listSearch=list.filter { it.modelprefix==checkEmpty }
            }
        }
        notifyDataSetChanged()

    }

}

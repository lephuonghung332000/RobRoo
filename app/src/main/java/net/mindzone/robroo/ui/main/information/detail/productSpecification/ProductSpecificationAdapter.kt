package net.mindzone.robroo.ui.main.information.detail.productSpecification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.productModel.entity.ResponseData
import net.mindzone.robroo.data.productModel.entity.getSpecification.Fields
import net.mindzone.robroo.utils.Animations
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

class ProductSpecificationAdapter(private  var context: Context,private var listTitle: List<ResponseData>): RecyclerView.Adapter<ProductSpecificationAdapter.ViewHolder>() {
    private lateinit var productSpecificationSubItemAdapter: ProductSpecificationSubItemAdapter
    private  var listSearch= arrayListOf<ResponseData>()
    init{
        listSearch.addAll(listTitle)
    }
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_specification, parent, false)
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_product_specification, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listSearch.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private fun toggleLayout(
                isExpanded: Boolean,
                v: View,
                layoutExpand: LinearLayout
        ): Boolean {
            Animations.toggleArrow(v, isExpanded)
            if (isExpanded) {
                Animations.expand(layoutExpand)

            } else {
               Animations.collapse(layoutExpand)
            }
            return isExpanded
        }
        private var isEpanded: Boolean = false
        var recyclerView: RecyclerView = view.findViewById(R.id.recyclerChildProductSpecification)
        var constraintLayout: ConstraintLayout = view.findViewById(R.id.layoutExpand)
        var linearLayout: LinearLayout = view.findViewById(R.id.layoutLinear)
        var btnMore: ImageButton = view.findViewById(R.id.btnArrrow)
        var txtProductSpecification: TextView =view.findViewById(R.id.txtTitleM)

        init {
            itemView.setOnClickListener(View.OnClickListener {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
                isEpanded=!isEpanded
                val show: Boolean =
                    toggleLayout(isEpanded, btnMore,linearLayout)
            })
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            productSpecificationSubItemAdapter = ProductSpecificationSubItemAdapter(context, listSearch.get(position).fields)
            holder.recyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = productSpecificationSubItemAdapter
        }
        holder.txtProductSpecification.text=listSearch[position].formtitle
    }
}

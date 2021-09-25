package net.mindzone.robroo.ui.main.information.detail.productSpecification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.R
import net.mindzone.robroo.data.productModel.entity.getSpecification.Fields

class ProductSpecificationSubItemAdapter(private val mContext: Context,private val list:ArrayList<Fields>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_TITLE = 1
    private val TYPE_CONTENT = 2
    init{
        list.add(0,Fields())
    }
    override fun getItemViewType(position: Int): Int {
        return if (position==0) {
            TYPE_TITLE
        } else {
            TYPE_CONTENT
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view: View
        return if (viewType == TYPE_TITLE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_product_specification_sub_item_title, parent, false)
            TitleViewHolder(view)
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_product_specification_sub_item_content, parent, false)
            ContentViewHolder(view)

        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.apply {
            when (holder) {
                is ContentViewHolder -> holder.onBind(list[position])
            }
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    class TitleViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }
    inner class ContentViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private var txtLabel: TextView =view.findViewById(R.id.txtLabel)
        private  var txtValue: TextView =view.findViewById(R.id.txtValue)
        fun onBind (fields: Fields){
            txtLabel.text = fields.fieldlabel
            txtValue.text = fields.fieldvalue
        }

    }




}
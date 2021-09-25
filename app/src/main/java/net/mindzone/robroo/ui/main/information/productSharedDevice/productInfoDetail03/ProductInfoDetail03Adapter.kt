package net.mindzone.robroo.ui.main.information.productSharedDevice.productInfoDetail03

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import co.lujun.androidtagview.TagContainerLayout
import co.lujun.androidtagview.TagView
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.productmodelListimplement.entity.ResponseData
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

class ProductInfoDetail03Adapter(var context:Context, var list: List<ResponseData>, var itemListener: (content: String) -> Unit): RecyclerView.Adapter<ProductInfoDetail03Adapter.ViewHolder>() {

    private  var listSearch=mutableSetOf<String>()
    private  var listFilter= mutableListOf<String>()
    init{
        for(i in list.indices){
            listSearch.add(list[i].modelprefix)
            listFilter.add(list[i].modelcode)
        }

    }
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shareddevice, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listSearch.size
    }
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var txtModelPrefix: TextView = view.findViewById(R.id.txtModelPrefix)
        var mTagContainerLayout: TagContainerLayout =view.findViewById(R.id.tagLayout)
        fun onBind(string: String){
            txtModelPrefix.text = string
            val typeface = ResourcesCompat.getFont(context, R.font.kanit_medium);
            mTagContainerLayout.tagTypeface=typeface
            mTagContainerLayout.setOnTagClickListener(object : TagView.OnTagClickListener {
                override fun onTagClick(position: Int, text: String) {
                    itemListener(text)
                    AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0, text)
                }

                override fun onTagLongClick(position: Int, text: String) {

                }

                override fun onSelectedTagDrag(position: Int, text: String) {

                }

                override fun onTagCrossClick(position: Int) {

                }
            })
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val listTest=listFilter.filter {it.contains(listSearch.elementAt(position))}
        holder.mTagContainerLayout.setTags(listTest)

        holder.onBind(listSearch.elementAt(position))


    }

}
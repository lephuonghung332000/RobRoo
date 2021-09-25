package net.mindzone.robroo.ui.main.information.productInfoWaranty

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.productgroupList.entity.ResponseData
import net.mindzone.robroo.ui.main.information.productInfoWaranty.productCategory02.ProductCategory02Fragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

class InfoWarantyAdapter (private val list: List<ResponseData>,
                          private val context: Context) :
    RecyclerView.Adapter<InfoWarantyAdapter.MyViewHolder>() {

    inner class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var txtinfoWaranty: TextView = view.findViewById(R.id.txtProductInfo)
        var constrainLayout: ConstraintLayout = view.findViewById(R.id.childProductInfo)

        fun onBind(string: ResponseData) {
            txtinfoWaranty.text=string.groupnameth
        }
        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                sendData(list[pos],txtinfoWaranty.text.toString())
                Log.d("higoai",list[pos].groupid.toString())
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,txtinfoWaranty.text.toString())
            }
        }
    }
    private fun sendData (re: ResponseData, title :String){
        val fragment = ProductCategory02Fragment()
        val bundle = Bundle()
        bundle.putString("title2",title)
        bundle.putSerializable("re2", re)
        fragment.arguments = bundle
        newFragment(fragment, context)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product_info, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(list[position])
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (position % 2 == 0) {
                holder.constrainLayout.setBackgroundColor(context.getColor(R.color.white))
            } else {
                holder.constrainLayout.setBackgroundColor(context.getColor(R.color.color_F5F5F5))
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

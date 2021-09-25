package net.mindzone.robroo.ui.main.service.kpdaService

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_information_detail.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.menu.entity.ListTopMostMenuItem.ResponseData
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager


class ServiceAdapter(
    var list: List<ResponseData>,
    private val context: Context,
    var itemListener: ((ResponseData) -> Unit)
) :
    RecyclerView.Adapter<ServiceAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var txtDroneService: TextView = view.findViewById(R.id.txtDroneService)
        var imgService:ImageView=view.findViewById(R.id.imgDrone)
        fun onBind(responseData: ResponseData) {
            txtDroneService.setText(responseData.titleth)
            Glide.with(context)
                .load(responseData.iconimageth)
                .error(R.drawable.ic_item_setting)
                .into(imgService)
        }

        init {
            itemView.setOnClickListener {
                itemListener(list[position])
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,txtDroneService.text.toString())
            }
        }
    }

}
package net.mindzone.robroo.ui.main.service.kpdaService
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.menu.entity.ListTopMostMenuItem.ResponseData
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

class KPDAServiceAdapter(var list: List<ResponseData>,
                         private val context: Context,
                         var itemListener: ((ResponseData) -> Unit)
                         )
    : RecyclerView.Adapter<KPDAServiceAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kpda_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var constrainLayout: ConstraintLayout = view.findViewById(R.id.constraintKPDA)
        var txtTitle=view.findViewById<TextView>(R.id.txtTitleSubMenu)
        var txtShort=view.findViewById<TextView>(R.id.txtShort)
        var imgService=view.findViewById<ImageView>(R.id.imgService)
        init {
                    itemView.setOnClickListener {
                        itemListener(list[position])
                        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,txtTitle.text.toString())
                    }
        }
        fun onBind(responseData: ResponseData) {
            //txtDroneService.setText(responseData.titleth)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (position % 2 == 0) {
                    constrainLayout.setBackgroundColor(context.getColor(R.color.white))
                } else {
                    constrainLayout.setBackgroundColor(context.getColor(R.color.color_F5F5F5))
                }
            }
            txtShort.text=responseData.shortdescth
            txtTitle.text=responseData.titleth
            Glide.with(context)
                .load(responseData.iconimageth)
                .error(R.drawable.ic_service)
                .into(imgService)

        }
    }
}
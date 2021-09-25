package net.mindzone.robroo.ui.main.service.droneService

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.R
class DroneSeviceAdapter(private val context: Context,
                         var itemListener: () -> Unit): RecyclerView.Adapter<DroneSeviceAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drone_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (position % 2 == 0) {
                holder.constrainLayout.setBackgroundColor(context.getColor(R.color.white))
            } else {
                holder.constrainLayout.setBackgroundColor(context.getColor(R.color.color_F5F5F5))
            }
        }
    }

    override fun getItemCount(): Int {
        return 11
    }
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var constrainLayout: ConstraintLayout = view.findViewById(R.id.constraintDrone)
        init {
            itemView.setOnClickListener {
                itemListener()
            }
        }
    }
}
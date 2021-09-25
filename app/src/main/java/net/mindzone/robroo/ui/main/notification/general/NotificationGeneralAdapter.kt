package net.mindzone.robroo.ui.main.notification.general


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.R
import net.mindzone.robroo.data.notification.entity.Notification


class NotificationGeneralAdapter(val context: Context?, val list : List<Notification>?) : RecyclerView.Adapter<NotificationGeneralAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(this.context).inflate(R.layout.cell_notification_general, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list?.get(position)!!)

        if(position % 2 != 0) {
            holder.setBackgroundColor()
        }
    }

    override fun getItemCount(): Int {
        Log.d("testatat", list?.size.toString())
        return list?.size!!

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemlayout = view.findViewById<ConstraintLayout>(R.id.itemLayout)!!
        private val tvTile = view.findViewById<TextView>(R.id.tvCategory)
        private val tvBody = view.findViewById<TextView>(R.id.tvBody)
        init {

        }
        fun onBind (notification: Notification){
            tvTile.text = notification.title
            tvBody.text = notification.body
        }
        fun setBackgroundColor(){
            itemlayout.setBackgroundResource(R.color.color_F5F5F5)
            Log.d("test", "Change color")
        }

    }



}
package net.mindzone.robroo.ui.main.notification.general



import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.R
import net.mindzone.robroo.data.notification.entity.Notification


class NotificationKnowledgeAdapter(val context: Context?, val list : List<Notification>) : RecyclerView.Adapter<NotificationKnowledgeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(this.context).inflate(R.layout.cell_notification_knowledge, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position % 2 != 0){
            holder.backgroundColor()
        }
    }

    override fun getItemCount(): Int {
        return list?.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var itemlayout = view.findViewById<ConstraintLayout>(R.id.itemLayoutKnowledge)!!
        init {

        }

        fun backgroundColor(){
            itemlayout.setBackgroundResource(R.color.color_F5F5F5)
            Log.d("test", "Change color")
        }

    }



}
package net.mindzone.robroo.ui.main.information.detail.shareddevice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.R

class SharedDeviceDetailAdapter: RecyclerView.Adapter<SharedDeviceDetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shared_device_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 3

    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.tvContent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      if(position==0){
          holder.textView.setSingleLine(true)
      }
    }


}
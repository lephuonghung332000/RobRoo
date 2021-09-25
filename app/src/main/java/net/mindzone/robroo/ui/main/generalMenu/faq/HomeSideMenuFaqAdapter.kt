package net.mindzone.robroo.ui.main.generalMenu.faq

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.faqList.entity.FaqList
import net.mindzone.robroo.ui.main.home.CustomClickToNewFragment
import net.mindzone.robroo.ui.main.generalMenu.applicationManual.applicationManualDetail.HomeSideMenuApplicationManualDetailFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

class HomeSideMenuFaqAdapter(val context: Context, val list: List<FaqList>) :
    RecyclerView.Adapter<HomeSideMenuFaqAdapter.ViewHolder>() , CustomClickToNewFragment {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_side_menu_faq, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])

        if (position % 2 != 0) {
            holder.setBackgroundColor()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemLayout = view.findViewById<ConstraintLayout>(R.id.itemLayoutFaq)
        private val tvTitle = view.findViewById<TextView>(R.id.tvCategory)
        private val imgIconFaq = view.findViewById<ImageView>(R.id.imgIconFaq)

        init {
            itemView.setOnClickListener {

                sendData(list[layoutPosition])
            }

        }
        private fun sendData(faqList: FaqList){
            val fragment = HomeSideMenuApplicationManualDetailFragment()
            val bundle = Bundle()
            bundle.putSerializable("data", faqList)
            fragment.arguments = bundle
            onClickNewFragment(fragment, context)
        }

        fun onBind(faqList: FaqList) {
            val url = faqList.icon
            Glide.with(itemView)
                .load(url)
                .error(R.drawable.ic_book)
                .placeholder(R.drawable.ic_book)
                .into(imgIconFaq)
            tvTitle.text = faqList.title
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,faqList.faq_id.toInt(),faqList.title)
        }

        fun setBackgroundColor() {
            itemLayout.setBackgroundResource(R.color.color_F5F5F5)
            Log.d("test", "Change color")
        }
    }

    override fun onClickNewFragment(newFragment: Fragment?, context: Context) {
        val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right)
        transaction
            .replace(R.id.frameMain, newFragment!!)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.addToBackStack(null)
        transaction.commit()
        Log.d("test", context.toString())
    }
}

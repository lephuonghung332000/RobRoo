package net.mindzone.robroo.ui.main.service.itemsService

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_item_service.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.ui.main.service.ServiceDetailFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

class ItemServiceFragment : BaseFragment(R.layout.fragment_item_service) {
    private lateinit var itemSeviceAdapter: ItemServiceAdapter
//    lateinit var txtTile: TextView
//    lateinit var mRecyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"ItemServiceFragment",0,"")
//        txtTile=view.findViewById(R.id.txtTile)
        txtTile.setText(getString(R.string.title_1))
        txtTile.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            getResources().getDimension(R.dimen.dp_18))
        val typeface = context?.let { ResourcesCompat.getFont(it, R.font.kanit_semibold) }
        txtTile.setTypeface(typeface)
//        mRecyclerView = view.findViewById(R.id.rvMenuItemService)
        rvMenuItemService.layoutManager = LinearLayoutManager(context)
        context?.let {
            itemSeviceAdapter = ItemServiceAdapter(it) {
                val serviceDetailFragment = ServiceDetailFragment()
                fragmentManager?.beginTransaction()
                    ?.setCustomAnimations(
                        R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left,
                        R.anim.slide_in_from_left,
                        R.anim.slide_out_to_right
                    //animation
                    )
                    ?.replace(R.id.frameMain, serviceDetailFragment)
                    ?.addToBackStack("")
               ?.commit()
            }
            rvMenuItemService.apply {
                adapter = itemSeviceAdapter
            }
        }
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {
    }


}
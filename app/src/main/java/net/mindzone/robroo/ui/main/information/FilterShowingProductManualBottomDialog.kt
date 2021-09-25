package net.mindzone.robroo.ui.main.information

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager


class FilterShowingProductManualBottomDialog  : BottomSheetDialogFragment() {
    private  lateinit var  tvCancel: LinearLayout
    private  lateinit var  tv_latest_manual: TextView
    private  lateinit var  tvfrequently_used_guide: TextView
    private var mListener: FilterClickListener? = null
    companion object{
        fun newInstance(): FilterShowingProductManualBottomDialog? {
            return FilterShowingProductManualBottomDialog()
        }
        const val TAG = "ActionBottomDialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = parentFragment as FilterClickListener?
        } catch (e: Exception) {
            //handle exception
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.filter_bottomsheet_productmanual, container, false)
        tvCancel = view.findViewById(R.id.linearLayoutCancel)
        tv_latest_manual = view.findViewById(R.id.tv_latest_manual)
        tvfrequently_used_guide = view.findViewById(R.id.tvfrequently_used_guide)
        setEvent()
        return view
    }

    private fun setEvent() {
        tvCancel.setOnClickListener {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,"product_manual_filter",0,this::class.qualifiedName)
            dismiss()
        }
        tv_latest_manual.setOnClickListener {
            mListener?.onFilterClick("popular");
            dismiss()
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,"product_manual_popular",0,this::class.qualifiedName)
        }
        tvfrequently_used_guide.setOnClickListener {
            mListener?.onFilterClick("lastest")
            dismiss()
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,"product_manual_used_guide",0,this::class.qualifiedName)
        }
    }


    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    interface FilterClickListener {
        fun onFilterClick(item: String)
    }

}
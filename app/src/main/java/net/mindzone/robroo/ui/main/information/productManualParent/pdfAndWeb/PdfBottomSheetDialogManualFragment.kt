package net.mindzone.robroo.ui.main.information.pdfDowloadedParent

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottomsheet_pdf_web.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.ui.main.idea.knowledge.detail.ActionBottom
import net.mindzone.robroo.ui.main.information.detail.BottomWarrantyFragment
import net.mindzone.robroo.ui.main.information.productManualParent.pdfAndWeb.ReadPdfOnlineFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager


class PdfBottomSheetDialogManualFragment  : BottomSheetDialogFragment() {

    private  lateinit var tvChooseWeb: TextView
    private  lateinit var tvChoosePdf: TextView
    private  lateinit var  tvCancel: LinearLayout
    private  lateinit var  viewHoz: View
    var linkViewOnline:String?=null
    private var mListener: ItemClickListener? = null
    companion object{
        fun newInstance(): PdfBottomSheetDialogManualFragment? {
            return PdfBottomSheetDialogManualFragment()
        }
        const val TAG = "ActionBottomDialog"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = parentFragment as ItemClickListener?
        } catch (e: Exception) {
            //handle exception
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.dialog_bottomsheet_pdf_web, container, false)
        tvChoosePdf = view.findViewById(R.id.tvChoosePdf)
        tvChooseWeb = view.findViewById(R.id.tvChooseWeb)
        viewHoz=view.findViewById(R.id.viewHoz)
        tvCancel = view.findViewById(R.id.btnCancelBottomsheetPdf)
        linkViewOnline=arguments?.getString("viewOnlineUrl")
        if(linkViewOnline==null){
            viewHoz.visibility=View.GONE
            tvChooseWeb.visibility=View.GONE
        }
        setEvent()
        return view
    }

    private fun setEvent() {
        tvChooseWeb.setOnClickListener {
            openBottomSheet()
            dismiss()
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0, "tvChooseWeb")
        }
        tvChoosePdf.setOnClickListener {
            mListener?.onItemClick(tvChoosePdf.text.toString())
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0, "tvChoosePdf")
            dismiss();
        }
        tvCancel.setOnClickListener{
            dismiss()
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0, "tvCancel")
        }
    }
    private fun openBottomSheet() {
        val webpagerDialogFragment = ReadPdfOnlineFragment()
        val bundle = Bundle()
        bundle.putString("viewOnlineUrl",linkViewOnline)
        webpagerDialogFragment.arguments = bundle
        webpagerDialogFragment.show(
            requireActivity().supportFragmentManager.beginTransaction(), "BottomSheet" )
}
    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    interface ItemClickListener {
        fun onItemClick(item: String?)
    }
}
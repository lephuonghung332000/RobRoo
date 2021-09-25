package net.mindzone.robroo.ui.main.information.pdfDowloadedParent

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
import net.mindzone.robroo.ui.main.idea.knowledge.detail.ActionBottom
import net.mindzone.robroo.ui.main.information.pdfDowloadedParent.readPdf.ReadFragment
import net.mindzone.robroo.ui.main.information.productManualParent.pdfAndWeb.ReadPdfOnlineFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

class PdfDowloadedBottomsheetDialogFragment  : BottomSheetDialogFragment() {
    private  lateinit var txtReadOnline: TextView
    private  lateinit var txtTitleBottomPdf: TextView
    private  lateinit var btnDelBottomSheet: TextView
    private  lateinit var btnCancelBottomsheetOpen: LinearLayout
    private  lateinit var  viewHoz: View
    var linkViewOnline:String?=null
    private var mListener: DeleteClickListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.dialog_dowloaded_popup_bottomsheet,container,false)
        txtTitleBottomPdf = view.findViewById(R.id.tvTitleBottomSheetPdf)
        txtReadOnline = view.findViewById(R.id.tvReadWeb)
        viewHoz=view.findViewById(R.id.viewWeb)
        btnDelBottomSheet=view.findViewById(R.id.btnDelBottomSheet)
        btnCancelBottomsheetOpen = view.findViewById(R.id.btnCancelBottomsheetOpen)
        linkViewOnline=arguments?.getString("viewOnlineUrl")
        if(linkViewOnline==null){
            viewHoz.visibility=View.GONE
            txtReadOnline.visibility=View.GONE
        }
        setEvent()
        return view
    }

    private fun setEvent() {
        txtTitleBottomPdf.setOnClickListener {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click, "manual_open_pdf",0,mListener?.readLinkPdf())
            val createFragment = ReadFragment()
            val bundle = Bundle()
            bundle.putString("linkPdf", mListener?.readLinkPdf())
            createFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_from_right,
                    R.anim.slide_out_to_left,
                    R.anim.slide_in_from_left,
                    R.anim.slide_out_to_right
                )
                .add(R.id.frameMain, createFragment).addToBackStack(null).commit()
            dismiss()
         }
        txtReadOnline.setOnClickListener {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click, "manual_read_online",0,mListener?.readLinkPdf())
            openBottomSheet()
            dismiss()
        }
        btnCancelBottomsheetOpen.setOnClickListener{
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
            dismiss()
        }
        btnDelBottomSheet.setOnClickListener {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
            mListener?.onDeleteClick("");
            dismiss()
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
    interface DeleteClickListener {
        fun onDeleteClick(item: String?)
        fun readLinkPdf():String
    }
    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = parentFragment as DeleteClickListener?
        } catch (e: Exception) {
            //handle exception
        }
    }

}
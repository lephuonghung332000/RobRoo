package net.mindzone.robroo.ui.main.generalMenu.homeSideMenuContact.contactBottomSheet

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_home_side_contact_call_bottomsheet.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.applicationSetting.entity.Application
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager


class ContactCallBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private  var application: Application? = null

    companion object {
        var mFragmentListener : FragmentListener? = null

        fun newInstance (fragmentListener: FragmentListener, modelToPass : Application) : ContactCallBottomSheetDialogFragment{
            val bottomSheetFragment = ContactCallBottomSheetDialogFragment()
            mFragmentListener = fragmentListener
            val bundle = Bundle()
            bundle.putSerializable("contactBottomSheet", modelToPass)
            bottomSheetFragment.arguments = bundle
            return bottomSheetFragment
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.dialog_home_side_contact_call_bottomsheet, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentListener?.getView(view)
        application = arguments?.getSerializable("contactBottomSheet") as Application
        setEvent()

    }


    fun setEvent() {
        lnLayoutCancel?.let {
            it.setOnClickListener {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
                Log.d("daclick", "da click cancel")
                dismiss()
            }
        }
        linearLayoutCall?.let{
            it.setOnClickListener {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
                Log.d("daclick", "da click")
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:" + application?.contactPhone)
                startActivity(intent)
            }
        }
    }
}
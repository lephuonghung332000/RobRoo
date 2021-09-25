package net.mindzone.robroo.ui.main.generalMenu.homeSideMenuContact.contactBottomSheet

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_home_side_contact_bottomsheet.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.applicationSetting.entity.Application
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager


class ContactEmailBottomSheetDiaLogFragment  : BottomSheetDialogFragment() {
    private  var application: Application? = null
    companion object{
        var mFragmentListener : FragmentListener? = null
        fun newInstance (fragmentListener: FragmentListener, modelToPass : Application) : ContactEmailBottomSheetDiaLogFragment{
            val bottomSheetFragment = ContactEmailBottomSheetDiaLogFragment()
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.dialog_home_side_contact_bottomsheet,container,false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ContactCallBottomSheetDialogFragment.mFragmentListener?.getView(view)
        application = arguments?.getSerializable("contactBottomSheet") as Application
        setEvent()
    }

    fun setEvent(){
        linearLayoutSendEmail.let{
            it.setOnClickListener {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
                sendEmail(application?.contactEmail!!)
            }
        }
        lnLayoutCancel?.let {
            it.setOnClickListener {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
                dismiss()
            }
        }
    }
    private fun sendEmail(recipient: String) {
        /*ACTION_SEND action to launch an email client installed on your Android device.*/
        val mIntent = Intent(Intent.ACTION_SEND)
        /*To send an email you need to specify mailto: as URI using setData() method
        and data type will be to text/plain using setType() method*/
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        //put the Subject in the intent



        try {
            //start email intent
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }

    }

}
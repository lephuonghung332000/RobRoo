package net.mindzone.robroo.ui.main.generalMenu.homeSideMenuContact

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home_side_menu_contact.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.applicationSetting.entity.Application
import net.mindzone.robroo.ui.main.generalMenu.homeSideMenuContact.contactBottomSheet.ContactEmailBottomSheetDiaLogFragment
import net.mindzone.robroo.ui.main.generalMenu.homeSideMenuContact.contactBottomSheet.ContactCallBottomSheetDialogFragment
import net.mindzone.robroo.ui.main.generalMenu.homeSideMenuContact.contactBottomSheet.FragmentListener
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class HomeSideMenuContactFragment : BaseFragment(R.layout.fragment_home_side_menu_contact),
    FragmentListener {
    private val viewModel by viewModels<HomeSideMenuContactViewModel>()
    private var application: Application? = null
    private lateinit var contactCallBottomSheetDiaLogFragment: ContactCallBottomSheetDialogFragment
    private var contactEmailBottomSheetDiaLogFragment = ContactEmailBottomSheetDiaLogFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        contactBottomSheetDiaLogFragment = ContactBottomSheetDiaLogFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"HomeSideMenuContactFragment",0,"")
        txtTile?.let { it.text = "ติดต่อผู้ดูแลระบบ" }
        setEvent()

        viewModel.getApplicationSetting()

    }


    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {
        viewModel.applicationSettingResponse.observe(viewLifecycleOwner) {
            tvPhone.text = it.contactPhone
            tvEmail.text = it.contactEmail
        }
    }

    private fun setEvent() {
        ctLayoutContact.setOnClickListener {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,"contact_phone",0,tvPhone.text.toString())
            openBottomSheetCall()

        }
        ctLayoutEmail.setOnClickListener {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,"contact_email",0,tvEmail.text.toString())
            openBottomSheetEmail()
        }
    }

    private fun openBottomSheetCall() {
        contactCallBottomSheetDiaLogFragment =
            ContactCallBottomSheetDialogFragment.newInstance(this,
                viewModel.applicationSettingResponse.value!!
            )
        contactCallBottomSheetDiaLogFragment.show(
            requireActivity().supportFragmentManager.beginTransaction(),
            "BottomSheet"
        )

    }


    private fun openBottomSheetEmail() {
        contactEmailBottomSheetDiaLogFragment = ContactEmailBottomSheetDiaLogFragment.newInstance(this,viewModel.applicationSettingResponse.value!!)
        contactEmailBottomSheetDiaLogFragment.show(
            requireActivity().supportFragmentManager.beginTransaction(), "BottomSheet")

    }

    override fun getView(view: View?) {
        view?.findViewById<TextView>(R.id.tvTitleBottomSheet)?.text = "โทรออก " + (viewModel.applicationSettingResponse.value?.contactPhone)
        view?.findViewById<TextView>(R.id.tvTitleEmailBottomSheet)?.text = "คัดลอก"
    }

}
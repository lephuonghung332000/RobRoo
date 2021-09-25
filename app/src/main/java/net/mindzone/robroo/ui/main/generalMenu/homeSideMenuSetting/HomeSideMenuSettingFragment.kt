package net.mindzone.robroo.ui.main.generalMenu.homeSideMenuSetting

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home_side_menu_setting.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.fcm.MyFirebaseMessagingService
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class HomeSideMenuSettingFragment : BaseFragment(R.layout.fragment_home_side_menu_setting) {
    val viewModel by viewModels<HomeSideMenuSettingViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"HomeSideMenuSettingFragment",0,"")
        //set title toolbar
        txtTile?.let { it.text = getString(R.string.setup) }

        event()
    }

    private fun event() {
        switchCompat.isChecked = viewModel.getStatusSwitch() == "1"

            switchCompat.setOnClickListener {

                if (switchCompat.isChecked){
                    AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click, "settings_switch_push_notifications",1,this::class.qualifiedName)
                    viewModel.setStatusSwitch("1")
                    switchCompat.isChecked = true
                    Log.d("xemthuthenao",viewModel.getStatusSwitch())
                    Toast.makeText(context, "On", Toast.LENGTH_SHORT).show()
                    setPushToken()
                }else{
                    AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click, "settings_switch_push_notifications",0,this::class.qualifiedName)
                    viewModel.setStatusSwitch("0")
                    switchCompat.isChecked = false
                    Log.d("xemthuthenao",viewModel.getStatusSwitch())
                    setPushToken()
                    Toast.makeText(context, "Off", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {

    }
    private fun setPushToken(){
        var token: String = MyFirebaseMessagingService.getToken(requireContext())
        Log.d("token push", token + " " )
        viewModel.setPushToken(token, MainActivity.user!!.azureoid)

    }
}
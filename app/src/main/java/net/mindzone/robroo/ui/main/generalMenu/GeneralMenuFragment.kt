package net.mindzone.robroo.ui.main.generalMenu

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_general_menu.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.ui.login.LoginActivity
import net.mindzone.robroo.ui.main.generalMenu.applicationManual.ApplicationManualFragment
import net.mindzone.robroo.ui.main.generalMenu.faq.HomeSideMenuFaqFragment
import net.mindzone.robroo.ui.main.generalMenu.homeSideMenuContact.HomeSideMenuContactFragment
import net.mindzone.robroo.ui.main.generalMenu.homeSideMenuSetting.HomeSideMenuSettingFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.Constants
import net.mindzone.sampleaudit.AuditManager
import net.openid.appauth.*


@AndroidEntryPoint
class GeneralMenuFragment : BaseFragment(R.layout.fragment_general_menu) {
    private val viewModel by viewModels<GeneralMenuViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEvent()
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"GeneralMenuFragment",0,"")

        try {
            val pInfo = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
            val version = pInfo.versionName
            versionName.text = "Versions $version"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {

    }

    private fun setEvent() {
        ctlayoutApplicationManual.setOnClickListener {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
            createFragment(ApplicationManualFragment())
        }

        ctLayoutSetting.setOnClickListener {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
            createFragment(HomeSideMenuSettingFragment())
        }

        ctLayoutSettingContact.setOnClickListener {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
            createFragment(HomeSideMenuContactFragment())
        }

        ctLayoutFAQ.setOnClickListener {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
            createFragment(HomeSideMenuFaqFragment())
        }
        contrainLogout.setOnClickListener {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
            val sharedPref: SharedPreferences = requireContext().getSharedPreferences( "comment", Context.MODE_PRIVATE
            )
            sharedPref.edit().clear().apply()
            viewModel.deleteUser()


           val mAuthService : AuthorizationService = AuthorizationService(requireActivity())
           val mServiceConfig = AuthorizationServiceConfiguration(
                Uri.parse(Constants.URL_LOGOUT),  // authorization endpoint
                Uri.parse(Constants.AZURE_mTokenEndpoint)
            )
            val mAuthState = AuthState(mServiceConfig!!)
            val authRequestBuilder = AuthorizationRequest.Builder(
                mServiceConfig!!,  // the authorization service configuration
                Constants.AZURE_kClientId,  // the client ID, typically pre-registered and static
                //ResponseTypeValues.CODE, // the response_type value: we want a code
                ResponseTypeValues.ID_TOKEN,  // <!-MUST BE THIS VALUE TO GET AZURE_ID BUT NEED TO WAIT FOR  CUSTOMER  TO ENABLE
                Uri.parse(Constants.AZURE_kRedirectUri)
            ) // the redirect URI to which the auth response is sent
            val authRequest = authRequestBuilder
                .setScope("openid email profile")
                .build()

            mAuthService!!.performAuthorizationRequest(
                authRequest,
                PendingIntent.getActivity(
                    requireActivity(),
                    0,
                    Intent(
                        requireActivity(),
                        LoginActivity::class.java
                    ),
                    0
                ),
                PendingIntent.getActivity(
                    requireActivity(),
                    0,
                    Intent(
                        requireActivity(),
                        LoginActivity::class.java
                    ),
                    0
                )
            )
        }
    }

    fun createFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.popBackStack()
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_right
            )
            ?.replace(R.id.frameMain, fragment)
            ?.addToBackStack(null)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            ?.commit()

    }

}
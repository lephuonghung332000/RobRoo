package net.mindzone.robroo.ui.login

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import dagger.hilt.android.AndroidEntryPoint
import net.mindzone.robroo.R
import net.mindzone.robroo.base.BaseActivity
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelper
import net.mindzone.robroo.data.sharedpref.SharedPreferencesHelperImp
import net.mindzone.robroo.databinding.FragmentSplashScreenLoginBinding
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.utils.Constants
import net.openid.appauth.*
import org.json.JSONObject


@Suppress("DEPRECATION")
@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private var mAuthService: AuthorizationService? = null
    private var mAuthState: AuthState? = null
    private var mServiceConfig: AuthorizationServiceConfiguration? = null

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //disable dark mode

        DataBindingUtil.setContentView<FragmentSplashScreenLoginBinding>(
            this,
            R.layout.fragment_splash_screen_login
        ).apply {
            fragmentViewModel = viewModel
            lifecycleOwner = this@LoginActivity
        }

        mAuthService = AuthorizationService(this@LoginActivity)
        mServiceConfig = AuthorizationServiceConfiguration(
            Uri.parse(Constants.AZURE_mAuthEndpoint),  // authorization endpoint
            Uri.parse(Constants.AZURE_mTokenEndpoint)
        ) // token endpoint

        mAuthState = AuthState(mServiceConfig!!)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        viewModel.apply {
            navigateToLogin.observe(this@LoginActivity) {
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
                        this@LoginActivity,
                        0,
                        Intent(
                            this@LoginActivity,
                            LoginActivity::class.java
                        ),
                        0
                    ),
                    PendingIntent.getActivity(
                        this@LoginActivity,
                        0,
                        Intent(
                            this@LoginActivity,
                            LoginActivity::class.java
                        ),
                        0
                    )
                )
            }
            navigateToStartActivity.observe(this@LoginActivity) {
                hideLoading()
                MainActivity.start(this@LoginActivity, viewModel.user.value!!)
            }
        }

        val redirectUri = intent.data
        if (redirectUri != null) {
            Log.e("ROBROO", redirectUri.toString())
            try {
                val fragments = redirectUri.fragment
                val newURL =
                    Uri.parse("https://www.robroo.com/index.php?$fragments") // recreate as url. Original is not  parsable
                val tokenId = newURL.getQueryParameter("id_token")
                if (tokenId != null) {
                    val string = tokenId.split("\\.".toRegex()).toTypedArray()
                    val jsonUser = JSONObject(
                        Base64.decode(string[1], Base64.DEFAULT).toString(charset("UTF-8"))
                    )
                    showLoading()
                    viewModel.login(jsonUser.getString("oid"))
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Authorization token failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Authorization failed", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mAuthService != null) {
            mAuthService!!.dispose()
        }
    }
}
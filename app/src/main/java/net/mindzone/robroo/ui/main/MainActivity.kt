package net.mindzone.robroo.ui.main

import android.R.id
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import net.mindzone.robroo.R
import net.mindzone.robroo.base.BaseActivity
import net.mindzone.robroo.data.user.entity.ResponseData
import net.mindzone.robroo.fcm.MyFirebaseMessagingService


@AndroidEntryPoint
class MainActivity() : BaseActivity(){
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var viewPager: ViewPager
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    companion object {
        /* user is satic  */
        var user: ResponseData? = null
        fun start(activity: Activity, user: ResponseData) {
            activity.startActivity(Intent(activity, MainActivity::class.java).apply {
                this.putExtra("user", user)
            })
            activity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
        }


    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //disable dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        firebaseAnalytics()
        setContentView(R.layout.activity_main)

        getWidget()
        setUpViewpager()
        setEvent()
        user = intent.getSerializableExtra("user") as ResponseData
        Log.e("user", user.toString())
        firtOpenApp()


    }

    private fun firebaseAnalytics() {
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
//        val bundle = Bundle()
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"")
//        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)


    }


    private fun getWidget() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        viewPager = findViewById(R.id.viewPagerContainer)
    }

    private fun setEvent() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> viewPager.currentItem = 0
                R.id.information -> viewPager.currentItem = 1
                R.id.service -> viewPager.currentItem = 2
                R.id.group -> viewPager.currentItem = 3
                R.id.idea -> viewPager.currentItem = 4
            }
            true
        }
    }

    private fun setUpViewpager() {
        val mainViewPagerAdapter: MainViewPagerAdapter = MainViewPagerAdapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        viewPager.adapter = mainViewPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            @SuppressLint("ResourceType")
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        bottomNavigationView.menu.findItem(R.id.home).isChecked = true
                        closeAllFragmentInFrameLayout()
                    }

                    1 -> {
                        bottomNavigationView.menu.findItem(R.id.information).isChecked = true
                        closeAllFragmentInFrameLayout()
                    }
                    2 -> {
                        bottomNavigationView.menu.findItem(R.id.service).isChecked = true
                        closeAllFragmentInFrameLayout()
                    }
                    3 -> {
                        bottomNavigationView.menu.findItem(R.id.group).isChecked = true
                        closeAllFragmentInFrameLayout()
                    }
                    4 -> {
                        bottomNavigationView.menu.findItem(R.id.idea).isChecked = true
                        closeAllFragmentInFrameLayout()
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun closeAllFragmentInFrameLayout() {

        val fm = supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }

    fun firtOpenApp(){
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            setPushToken()
            // mark first time has ran.
            val editor = prefs.edit()
            editor.putBoolean("firstTime", true)
            editor.commit()
        }
    }

    private fun setPushToken(){
//        val deviceToken: String = Settings.Secure.getString(
//            applicationContext.contentResolver,
//            Settings.Secure.ANDROID_ID
//        )
//
        var token: String = MyFirebaseMessagingService.getToken(this)
        Log.d("token push", token)
        if (token != ""){
            viewModel.setPushToken(token,user!!.azureoid)
        }else {
            Toast.makeText(this, "token empty", Toast.LENGTH_LONG).show()
        }
    }
}
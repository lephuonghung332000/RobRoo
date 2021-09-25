package net.mindzone.robroo.base


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.view_custom_toolbar01.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.generalMenu.GeneralMenuFragment
import net.mindzone.robroo.ui.main.notification.NotificationGeneralAndKnowledgeFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.customize.LoadingProgress
import net.mindzone.sampleaudit.AuditManager


/**
 * Created by Alaa Moataz on 9/6/20.
 *
 */
abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {
    private val viewModel by viewModels<BaseFragmentVM>()
    private var loadingProgress: LoadingProgress? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewDataBinding = DataBindingUtil.bind<ViewDataBinding>(view)
        setViewModel(viewDataBinding)
        viewDataBinding?.lifecycleOwner = this.viewLifecycleOwner
        startObservingValues()
        setEventForToolbar()
        fillDataForToolbar()
    }

    abstract fun setViewModel(viewDataBinding: ViewDataBinding?)
    abstract fun startObservingValues()


    private fun setEventForToolbar() {

        imgAvt?.let {
            it.setOnClickListener {
//                Toast.makeText(context, "toolbar clicked", Toast.LENGTH_SHORT).show()
                val fragment = fragmentManager?.findFragmentById(R.id.frameMain)
                if (fragment == null) {
                    val generalMenuFragment = GeneralMenuFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                            ?.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_left)
                            ?.add(R.id.frameMain, generalMenuFragment)
                            ?.addToBackStack(null)
                            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            ?.commit()
                } else {
                    requireActivity().onBackPressed()
                }
            }

        }
        //set click for imgBack
        imgBack?.let { it.setOnClickListener {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
            requireActivity().onBackPressed() } }

        //setclick for notification
        imgNotification?.let{
            it.setOnClickListener {
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
                if (checkGeneralExist()){
                    requireActivity().supportFragmentManager.popBackStack()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.setCustomAnimations(
                            R.anim.slide_in_from_right,
                            R.anim.slide_out_to_left,
                            R.anim.slide_in_from_left,
                            R.anim.slide_out_to_right
                        )
                        ?.add(R.id.frameMain, NotificationGeneralAndKnowledgeFragment())
                        ?.addToBackStack("NotificationGeneralAndKnowledgeFragment")
                        ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        ?.commit()
                }else {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.setCustomAnimations(
                            R.anim.slide_in_from_right,
                            R.anim.slide_out_to_left,
                            R.anim.slide_in_from_left,
                            R.anim.slide_out_to_right
                        )
                        ?.add(R.id.frameMain, NotificationGeneralAndKnowledgeFragment())
                        ?.addToBackStack("NotificationGeneralAndKnowledgeFragment")
                        ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        ?.commit()
                }
            }

        }

        imgNotificationToolbar2?.let{
            it.setOnClickListener {
                //AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)
                if (!isFragmentPresent()){
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.setCustomAnimations(
                            R.anim.slide_in_from_right,
                            R.anim.slide_out_to_left,
                            R.anim.slide_in_from_left,
                            R.anim.slide_out_to_right
                        )
                        ?.add(R.id.frameMain, NotificationGeneralAndKnowledgeFragment())
                        ?.addToBackStack("NotificationGeneralAndKnowledgeFragment")
                        ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        ?.commit()
                }
                Log.d("test", "return is : " + isFragmentPresent().toString())

            }
        }


    }

    @SuppressLint("SetTextI18n")
    private fun fillDataForToolbar(){
        val user = MainActivity.user
        tvCategory?.let{
            it.text = user?.firstnameth +" "+ user?.lastnameth
        }
        tvDomain?.let {
            it.text = MainActivity.user?.positionth
        }
//        txtProgress?.let { it.text = user?.exp.toString() + "/" + user?.expMax + " EXP"  }
//
//        progressBar?.let {
//            it.progress = user?.exp!!
//        }
    }

    open fun isFragmentPresent(): Boolean {
        val frags =  requireActivity().supportFragmentManager.findFragmentById(R.id.frameMain)

        if (frags is NotificationGeneralAndKnowledgeFragment){
            return true
        }
        return false
    }
    open fun checkGeneralExist(): Boolean {
        val frags =  requireActivity().supportFragmentManager.findFragmentById(R.id.frameMain)
        if (frags is GeneralMenuFragment){
            return true
        }
        return false
    }
    fun showLoading() {
        if (loadingProgress == null) {
            loadingProgress = context?.let { LoadingProgress(it) }
        }
        loadingProgress?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    fun hideLoading() {
        try {
            loadingProgress?.let {
                it.dismiss()
                loadingProgress = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
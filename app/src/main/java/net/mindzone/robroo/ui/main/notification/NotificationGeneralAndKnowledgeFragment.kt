package net.mindzone.robroo.ui.main.notification

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notification_general_and_knowledge.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.ui.login.LoginViewModel
import net.mindzone.robroo.ui.main.notification.general.NotificationGeneralFragment
import net.mindzone.robroo.ui.main.notification.knowledge.NotificationKnowledgeFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class NotificationGeneralAndKnowledgeFragment : BaseFragment(R.layout.fragment_notification_general_and_knowledge) {

    private val viewModel by viewModels<NotificationGeneralAndKnowledgeViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"notifications_menu",0,"")
        txtTile?.let { it.text = getString(R.string.notifications) }
        setUpViewPagerAndTabLayout()

    }
    private fun setUpViewPagerAndTabLayout(){
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.addFragment(NotificationGeneralFragment(), getString(R.string.general))
        viewPagerAdapter.addFragment(NotificationKnowledgeFragment(), getString(R.string.knowledge))
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {

    }

    inner class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager){
        private val fragment  = ArrayList<Fragment>()
        private val title  = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return fragment[position]
        }

        override fun getCount(): Int {
            return fragment.size
        }

        fun addFragment(fragment: Fragment, title: String){
            this.fragment.add(fragment)
            this.title.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return title[position]
        }

    }
}
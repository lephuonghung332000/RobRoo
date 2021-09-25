package net.mindzone.robroo.ui.main.idea.knowledge

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_all_and_unread_knowledge.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.ui.main.idea.IdeaViewModel
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager
import java.util.*

@AndroidEntryPoint
class AllAndUnreadKnowledgeFragment : BaseFragment(R.layout.fragment_all_and_unread_knowledge) {
    private val viewModel by viewModels<IdeaViewModel>()
    var title = ""
    var group_id=0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"AllAndUnreadKnowledgeFragment",0,"")
        title=arguments?.getString("tittle5")!!
        txtTile.text=title
        group_id=arguments?.getInt("id5")!!

        setUpViewPagerAndTabLayout()

    }

    private fun setUpViewPagerAndTabLayout(){
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        val allFlashKnowledgesFragment = AllFlashKnowledgesFragment.newInstance(group_id)

        val unreadKnowledgeFragment = UnreadKnowledgeFragment.newInstance(group_id)
        viewPagerAdapter.addFragment(allFlashKnowledgesFragment, getString(R.string.list_all))
        viewPagerAdapter.addFragment(unreadKnowledgeFragment, getString(R.string.unread))
        viewPagerMenu5.adapter = viewPagerAdapter
        tabLayoutMenu5.setupWithViewPager(viewPagerMenu5)
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

        override fun getPageTitle(position: Int): CharSequence? {
            return title[position]
        }

    }
}
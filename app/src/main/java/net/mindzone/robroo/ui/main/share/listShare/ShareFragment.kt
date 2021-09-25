package net.mindzone.robroo.ui.main.share.listShare

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list_all.*
import kotlinx.android.synthetic.main.fragment_share.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.view.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.forumArticle.entity.forumList.ForumGroups
import net.mindzone.robroo.databinding.FragmentShareBinding
import net.mindzone.robroo.ui.main.share.listShare.viewPager.listAll.ListAllFragment
import net.mindzone.robroo.ui.main.share.listShare.viewPager.MyBlogListFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class ShareFragment : BaseFragment(R.layout.fragment_share), ShareNavigator {

    private val viewModel by viewModels<ShareViewModel>()
    private var countClickButtonListAll = 1
    private var countClickButtonMyBlock = 0
//    private lateinit var forumGroups: ForumGroups

    companion object {
        fun newInstance() = ShareFragment()
        const val LIST_ALL_FRAGMENT = "ListAllFragment"
        const val MY_BLOG_LIST_FRAGMENT = "MyBlogListFragment"
        const val EXTRA_FORUM_GROUP = "ForumGroup"
        var forumGroups: ForumGroups? = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"firstpageshare_menu",0,"")
        includeCustomToolbar02.txtTile.text = "แบ่งปัน"
        navigateFragment(ListAllFragment(), false)
        viewModel.setNavigator(this)
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {
        (viewDataBinding as FragmentShareBinding).apply {
            fragmentViewModel = viewModel
            lifecycleOwner = this@ShareFragment
        }
        forumGroups = arguments?.getSerializable(EXTRA_FORUM_GROUP) as ForumGroups
    }

    override fun startObservingValues() {

    }

    private fun navigateFragment(fm: Fragment, slide: Boolean) {
        val bundle = Bundle()
        bundle.putSerializable(EXTRA_FORUM_GROUP, forumGroups)
        fm.arguments = bundle
        val transaction: FragmentTransaction =
            childFragmentManager.beginTransaction()
        if (slide) transaction.setCustomAnimations(
            R.anim.slide_in_from_left,
            R.anim.slide_out_to_right,
            R.anim.slide_in_from_left,
            R.anim.slide_out_to_right
        )
        else transaction.setCustomAnimations(
            R.anim.slide_in_from_right,
            R.anim.slide_out_to_left,
            R.anim.slide_in_from_left,
            R.anim.slide_out_to_right
        )
        transaction.replace(R.id.frameLayoutShare, fm).commit()
    }


    override fun onClickToggle(fm: String) {
        if (countClickButtonListAll == 0 && fm == LIST_ALL_FRAGMENT) {
            navigateFragment(ListAllFragment(), true)
            countClickButtonListAll = 1
            countClickButtonMyBlock = 0
        }
        if (countClickButtonMyBlock == 0 && fm == MY_BLOG_LIST_FRAGMENT) {
            navigateFragment(MyBlogListFragment(), false)
            countClickButtonMyBlock = 1
            countClickButtonListAll = 0
        }
    }

}
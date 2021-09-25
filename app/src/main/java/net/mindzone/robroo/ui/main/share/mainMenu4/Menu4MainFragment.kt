package net.mindzone.robroo.ui.main.share.mainMenu4

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_idea.*
import kotlinx.android.synthetic.main.fragment_menu4_main.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.forumArticle.entity.forumList.ForumGroups
import net.mindzone.robroo.databinding.FragmentMenu4MainBinding
import net.mindzone.robroo.ui.main.share.listShare.ShareFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class Menu4MainFragment : BaseFragment(R.layout.fragment_menu4_main) {

    private val viewModel by viewModels<Menu4MainViewModel>()
    private lateinit var menu4MainAdapter: Menu4MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"forumGroup",0,"")
        menu4MainAdapter = Menu4MainAdapter(viewModel){
            navigateToShareFragment(it)
        }
        rvMenu4Home.setAdapter(menu4MainAdapter)
        rvMenu4Home.apply {
            setLayoutManager(LinearLayoutManager(context))
            setVeilLayout(R.layout.cell_skeleton)
            addVeiledItems(7)
        }
        viewModel.getForumList()
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {
        (viewDataBinding as FragmentMenu4MainBinding).apply {
            fragmentViewModel = viewModel
        }
    }

    override fun startObservingValues() {
        viewModel.forumListResponse.observe(viewLifecycleOwner) {
            if(it.isSuccessful) {

                if(it.body()!!.responseData.forumGroups.isNotEmpty()) {
                    menu4MainAdapter.submitList(it.body()!!.responseData.forumGroups)
                }
            }
        }
        viewModel.status.observe(viewLifecycleOwner){
            when(it){
                Status.LOADING->{
                    rvMenu4Home.veil()
                }
                Status.SUCCESS->{
                    rvMenu4Home.unVeil()
                }
            }

        }
    }

    private fun navigateToShareFragment(forumGroups: ForumGroups) {
        val bundle = Bundle()
        bundle.putSerializable(ShareFragment.EXTRA_FORUM_GROUP, forumGroups)
        val fmShare = ShareFragment()
        fmShare.arguments = bundle
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.slide_in_from_right,
            R.anim.slide_out_to_left,
            R.anim.slide_in_from_left,
            R.anim.slide_out_to_right
        )
            .replace(R.id.frameMain, fmShare)
            .addToBackStack(null)
            .commit()
    }

}
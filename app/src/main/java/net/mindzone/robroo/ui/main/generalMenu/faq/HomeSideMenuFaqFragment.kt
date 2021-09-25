package net.mindzone.robroo.ui.main.generalMenu.faq

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_side_menu_faq.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.faqList.entity.FaqList
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class HomeSideMenuFaqFragment : BaseFragment(R.layout.fragment_side_menu_faq) {
    private val viewModel by viewModels<HomeSideMenuFaqViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"faq_menu",0,"")
        txtTile?.let { it.text = getString(R.string.faq) }

        viewModel.getFaqList("1")
    }
    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {
        viewModel.apply {
            faqListResponse.observe(viewLifecycleOwner) {
                setAdapter(it)
            }
        }
    }

    fun setAdapter(list: List<FaqList>){
       val homeSideMenuFaqAdapter = HomeSideMenuFaqAdapter(requireActivity(),list)
        rvHomeSideMenuFaq.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = homeSideMenuFaqAdapter
        }
    }
}
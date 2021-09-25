package net.mindzone.robroo.ui.main.generalMenu.applicationManual

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_application_manual.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.faqList.entity.FaqList
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager


@AndroidEntryPoint
class ApplicationManualFragment : BaseFragment(R.layout.fragment_application_manual){
     private val viewModel by viewModels<ApplicationManualViewModel>()
    private lateinit var applicationManualAdapter: ApplicationManualAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"manual_menu",0,"")
        txtTile?.let { it.text = getString(R.string.application_manual) }

        viewModel.getFaqList("2")

    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {
        viewModel.apply {
            faqListResponse.observe(viewLifecycleOwner) {
                setAdapter(it)
                Log.d("_faqListResponse", it.size.toString())
            }
        }
    }
    fun setAdapter(list: List<FaqList>){
        applicationManualAdapter = ApplicationManualAdapter(requireActivity(), list)
        rvApplicationManual.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = applicationManualAdapter
        }
    }
}
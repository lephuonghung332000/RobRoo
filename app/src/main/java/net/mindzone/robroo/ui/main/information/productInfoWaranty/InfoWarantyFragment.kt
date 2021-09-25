package net.mindzone.robroo.ui.main.information.productInfoWaranty

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_info_waranty.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.productgroupList.entity.ResponseData
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.information.InformationViewModel
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class InfoWarantyFragment : BaseFragment(R.layout.fragment_info_waranty) {
    private val viewModel by viewModels<InformationViewModel>()
    private lateinit var infoWarantyAdapter: InfoWarantyAdapter
    val azureId = MainActivity.user!!.azureoid
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"InfoWarantyFragment",0,"")
        txtTile?.let { it.text = getString(R.string.infomation_waranty) }
        viewModel.getSubGroups(azureId)
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {
        viewModel.apply {
            subGroupsResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null){
                        setAdapter(it.body()?.responseData!!) }
                    else{
                        rvMenu2ProductinfoWaranty.visibility = View.INVISIBLE
                        layoutNoDataI8.visibility = View.VISIBLE
                    }
                } else {

                }

            }
            status.observe(viewLifecycleOwner){
                Log.d("status",status.value.toString())
                when(it){
                    Status.LOADING->rvMenu2ProductinfoWaranty.veil()
                    Status.SUCCESS->rvMenu2ProductinfoWaranty.unVeil()
                }

            }
        }
    }

    private fun setAdapter(list: List<ResponseData>) {
        context?.let {
            infoWarantyAdapter = InfoWarantyAdapter(list,requireActivity())
            rvMenu2ProductinfoWaranty!!.apply {
                setAdapter(infoWarantyAdapter)
                setLayoutManager(LinearLayoutManager(context))
                setVeilLayout(R.layout.cell_skeleton)
                addVeiledItems(7)
            }

        }
    }
}
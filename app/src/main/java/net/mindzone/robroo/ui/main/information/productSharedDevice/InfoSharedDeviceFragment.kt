package net.mindzone.robroo.ui.main.information.productSharedDevice

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_info_shared_device.*
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
class InfoSharedDeviceFragment : BaseFragment(R.layout.fragment_info_shared_device) {
    private val viewModel by viewModels<InformationViewModel>()
    val azureId = MainActivity.user?.azureoid!!
    private lateinit var infoSharedDeviceAdapter: InfoSharedDeviceAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"InfoSharedDeviceFragment",0,"")
        txtTile?.let { it.text = getString(R.string.infomation_shared_device) }
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
                        rvMenu2ProductinfoSharedDevice.visibility = View.INVISIBLE
                        layoutNoDataI10.visibility = View.VISIBLE
                    }
                } else {

                }

            }
            status.observe(viewLifecycleOwner){
                when(it){
                    Status.LOADING->rvMenu2ProductinfoSharedDevice.veil()
                    Status.SUCCESS->rvMenu2ProductinfoSharedDevice.unVeil()
                }

            }
        }
    }

    private fun setAdapter(list: List<ResponseData>) {
        context?.let {
            infoSharedDeviceAdapter = InfoSharedDeviceAdapter(list,requireActivity())
            rvMenu2ProductinfoSharedDevice!!.apply {
                setAdapter(infoSharedDeviceAdapter)
                setLayoutManager(LinearLayoutManager(context))
                setVeilLayout(R.layout.cell_skeleton)
                addVeiledItems(7)
            }

        }
    }
}
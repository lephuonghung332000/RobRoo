package net.mindzone.robroo.ui.main.service

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_service.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.menu.entity.ListTopMostMenuItem.ResponseData
import net.mindzone.robroo.ui.main.information.detail.BottomWarrantyFragment
import net.mindzone.robroo.ui.main.service.kpdaService.KPDAServiceFragment
import net.mindzone.robroo.ui.main.service.kpdaService.ServiceAdapter
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class ServiceFragment : BaseFragment(R.layout.fragment_service) {
    private lateinit var serviceAdapter: ServiceAdapter
    private val viewModel by viewModels<ServiceViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"service_menu",0,"")
        viewModel.getListService("")
        veilRecyclerViewService!!.apply {
            setLayoutManager(LinearLayoutManager(context))
            setVeilLayout(R.layout.cell_skeleton)
            addVeiledItems(7)
        }

    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {
        viewModel.apply {
            listServiceResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null) {
                        setAdapter(it.body()?.responseData!!)
                        viewModel.setStatus()
                    }
                    else {

                    }
                }
            }
            status.observe(viewLifecycleOwner){
                Log.d("status",status.value.toString())
                when(it){
                    Status.LOADING->veilRecyclerViewService.veil()
                    Status.SUCCESS->veilRecyclerViewService.unVeil()
                }

            }
        }
    }
    //setup adapter for all recyclerview in fragment
    private fun setAdapter(list: List<ResponseData>) {
        serviceAdapter = context?.let { ServiceAdapter(list,it,itemListener = {
                if(it.actionlinkth.contains("http")){
                    val webpagerDialogFragment = BottomWarrantyFragment()
                    val bundle = Bundle()
                    bundle.putString("linkService",it.actionlinkth)
                    webpagerDialogFragment.arguments = bundle
                    webpagerDialogFragment.show(
                        requireActivity().supportFragmentManager.beginTransaction(), "BottomSheet"
                    )
                }
                else{
                    val kpdaServiceFragment = KPDAServiceFragment()
                    val bundle = Bundle()
                    var result = it.actionlinkth.filter { it.isDigit()}
                    bundle.putString("kpda",result)
                    bundle.putString("titleService",it.titleth)
                    kpdaServiceFragment.arguments = bundle
                    fragmentManager?.beginTransaction()
                        ?.setCustomAnimations(
                            R.anim.slide_in_from_right,
                            R.anim.slide_out_to_left,
                            R.anim.slide_in_from_left,
                            R.anim.slide_out_to_right
                        )
                        ?.replace(R.id.frameMain, kpdaServiceFragment)?.addToBackStack("")?.commit()
                }
        }) }!!
        veilRecyclerViewService.setAdapter(serviceAdapter)
//        Log.d("ben",productSpecificationAdapter.filterList(search_bar_info.filter(search_bar_info.getTextFilter())))
    }

    }
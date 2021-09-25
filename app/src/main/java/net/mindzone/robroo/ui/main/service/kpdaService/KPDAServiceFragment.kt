package net.mindzone.robroo.ui.main.service.kpdaService

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_k_p_d_a_service.*
import kotlinx.android.synthetic.main.fragment_service.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.menu.entity.ListTopMostMenuItem.ResponseData
import net.mindzone.robroo.ui.main.information.detail.BottomWarrantyFragment
import net.mindzone.robroo.ui.main.service.ServiceViewModel
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class KPDAServiceFragment : BaseFragment(R.layout.fragment_k_p_d_a_service) {
    private lateinit var kpdaServiceAdapter: KPDAServiceAdapter
    private val viewModel by viewModels<ServiceViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"KPDAServiceFragment",0,"")
        txtTile.setText(arguments?.getString("titleService")!!)
        txtTile.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            getResources().getDimension(R.dimen.dp_18))
        val typeface = context?.let { ResourcesCompat.getFont(it, R.font.kanit_semibold) }
        txtTile.setTypeface(typeface)
        viewModel.getListService(arguments?.getString("kpda")!!)
        rvMenu2KPDA.setLayoutManager(LinearLayoutManager(context))
        context?.let {
            rvMenu2KPDA.run{
                setVeilLayout(R.layout.cell_skeleton)
                addVeiledItems(15)
            }
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
                    }
                    else {

                    }
                }
            }
            status.observe(viewLifecycleOwner){
                Log.d("status",status.value.toString())
                when(it){
                    Status.LOADING->rvMenu2KPDA.veil()
                    Status.SUCCESS->rvMenu2KPDA.unVeil()
                }
            }
        }
    }
    private fun setAdapter(list: List<ResponseData>) {
        kpdaServiceAdapter = context?.let { KPDAServiceAdapter(list,it,itemListener = {
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
        rvMenu2KPDA.setAdapter(kpdaServiceAdapter)
    }


}
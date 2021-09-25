package net.mindzone.robroo.ui.detail.shareddevice

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_shared_device.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.productmodelListimplement.entity.ResponseData
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.information.InformationDetailFragment
import net.mindzone.robroo.ui.main.information.InformationViewModel
import net.mindzone.robroo.ui.main.information.detail.shareddevice.ShareDeviceAdapter
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class SharedDeviceFragment: BaseFragment(R.layout.fragment_shared_device) {
    private lateinit var shareDeviceAdapter: ShareDeviceAdapter
    private val viewModel by viewModels<InformationViewModel>()
    var azure_id= MainActivity.user?.azureoid!!
    fun getModelnameUrlSafe(model: String):String {
        return model.replace(" ", "%20").replace(",", "%2C")
    }
    var model_id=""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"SharedDeviceFragment",0,arguments?.getString("id_sharedevice")!!)
        model_id=getModelnameUrlSafe(arguments?.getString("id_sharedevice")!!)
        viewModel.getListImplement(azure_id,model_id)
        recyclerShareDevice.layoutManager=LinearLayoutManager(context)
    }
    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }
    override fun onResume() {
        super.onResume()
        layoutShareDevice.requestLayout()
    }
    override fun startObservingValues() {
        viewModel.apply {
            listImplementResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null) {
                        setAdapter(it.body()?.responseData!!)

                    } else {
                        recyclerShareDevice.visibility = View.INVISIBLE
                        layoutNodataShare.visibility = View.VISIBLE
                        txtShareDevice.visibility=View.INVISIBLE
                    }
                }
            }
        }
    }
    companion object {
        fun newInstance(id: String): Fragment {
            val f = SharedDeviceFragment()
            val b = Bundle()
            b.putString("id_sharedevice", id)
            f.arguments = b
            return f
        }
    }
    //setup adapter for all recyclerview in fragment
    private fun setAdapter(list:List<ResponseData>){
        shareDeviceAdapter = context?.let { ShareDeviceAdapter(it, list,itemListener = {
            val createFragment = InformationDetailFragment()
            val bundle = Bundle()
            bundle.putString("model_id", it)
            createFragment.arguments = bundle
            fragmentManager?.beginTransaction()?.setCustomAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_left,
                R.anim.slide_out_to_right
            )
                ?.add(R.id.frameMain, createFragment)?.addToBackStack(null)?.commit()
        })
        }!!
        recyclerShareDevice.setAdapter(shareDeviceAdapter)
    }

}
package net.mindzone.robroo.ui.main.information.productSharedDevice.productInfoDetail03

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_info_detail03.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import kotlinx.android.synthetic.main.view_layout_title02.view.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.productInfo.entity.ResponseData
import net.mindzone.robroo.ui.main.information.InformationDetailFragment
import net.mindzone.robroo.ui.main.information.InformationViewModel
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class ProductInfoDetail03Fragment: BaseFragment(R.layout.fragment_product_info_detail03) {
    private val viewModel by viewModels<InformationViewModel>()
    private var title:String=""
    private var model_id=""

    private lateinit var productInfoDetailAdapter: ProductInfoDetail03Adapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title=arguments?.getString("title5")!!
        txtTile.text=title
        model_id=title
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"ProductInfoDetail03Fragment",0,title)
        viewModel.getInfo("ff0d52e8-5a85-4650-87c3-78842cdb8e69",arguments?.getString("title5")!!)
        viewModel.getListImplement("ff0d52e8-5a85-4650-87c3-78842cdb8e69",arguments?.getString("title5")!!)
        rvProductInfoDetail03!!.apply {
            setLayoutManager(LinearLayoutManager(context))
            setVeilLayout(R.layout.cell_skeleton)
            addVeiledItems(10)
        }

    }


    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {
        viewModel.apply {
            infoResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null) {
                        addEvent(it.body()?.responseData!!)

                    } else {
                    }
                }
            }
            status.observe(viewLifecycleOwner) {
                Log.d("status", status.value.toString())
                when (it) {

                }

            }
        }
        viewModel.apply {
            listImplementResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null) {
                        setAdapter(it.body()?.responseData!!)

                    } else {
//                        rvProductInfoDetail03.visibility = View.INVISIBLE
//                        layoutNodataShare.visibility = View.VISIBLE
//                        txtShareDevice.visibility=View.INVISIBLE
                    }
                }
                else{
//                    recyclerShareDevice.visibility = View.INVISIBLE
//                    layoutNodataShare.visibility = View.VISIBLE
//                    txtShareDevice.visibility=View.INVISIBLE
                }
            }
            status.observe(viewLifecycleOwner) {
                when (it) {
                    Status.LOADING->rvProductInfoDetail03.veil()
                    Status.SUCCESS->rvProductInfoDetail03.unVeil()
                }

            }
        }

    }

    private fun setAdapter(list: List<net.mindzone.robroo.data.productmodelListimplement.entity.ResponseData>) {

        productInfoDetailAdapter = context?.let { ProductInfoDetail03Adapter(it, list,itemListener = {
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
        })}!!
        rvProductInfoDetail03.setAdapter(productInfoDetailAdapter)
    }

    private fun addEvent(item: ResponseData) {
        Glide.with(requireContext()).load(item.model_image).into(imgView05)
        layoutTitle05.textViewTitleSeries05.text= item.model_code
    }


}
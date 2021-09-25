package net.mindzone.robroo.ui.main.information.productInfo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_info.*
import kotlinx.android.synthetic.main.fragment_product_info_detail03.*
import net.mindzone.robroo.R
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.productgroupList.entity.ResponseData
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.information.InformationDetailFragment
import net.mindzone.robroo.ui.main.information.InformationViewModel
import net.mindzone.robroo.ui.main.information.productCategory.ProductCategoryFragment
import net.mindzone.robroo.utils.extensions.Status

@AndroidEntryPoint
class ProductInfoFragment : BaseFragment(R.layout.fragment_product_info) {
    private  lateinit var txtTitle: TextView
    private val viewModel by viewModels<InformationViewModel>()
    private lateinit var productInfoAdapter: ProductInfoAdapter

    val azureId = MainActivity.user!!.azureoid
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtTitle = view.findViewById(R.id.txtTile)
        txtTitle.text = "ข้อมูลสินค้า"
        viewModel.getSubGroups(azureId)
        rvMenu2ProductInfo!!.apply {
            setLayoutManager(LinearLayoutManager(context))
            setVeilLayout(R.layout.cell_skeleton)
            addVeiledItems(7)
        }

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
                        rvMenu2ProductInfo.visibility = View.INVISIBLE
                        layoutNoDataI1.visibility = View.VISIBLE
                    }
                } else {

                }

            }
            status.observe(viewLifecycleOwner){
                Log.d("status",status.value.toString())
                when(it){
                    Status.LOADING->rvMenu2ProductInfo.veil()
                    Status.SUCCESS->rvMenu2ProductInfo.unVeil()
                }

            }
        }
    }
    private fun setAdapter(list: List<ResponseData>) {
            context?.let {
                productInfoAdapter = ProductInfoAdapter(list,requireActivity())
            }
        rvMenu2ProductInfo.setAdapter(productInfoAdapter)
    }
}
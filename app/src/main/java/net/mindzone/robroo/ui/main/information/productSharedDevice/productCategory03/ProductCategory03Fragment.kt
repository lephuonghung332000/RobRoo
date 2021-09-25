package net.mindzone.robroo.ui.main.information.productSharedDevice.productCategory03

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_category03.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.productgroupList.entity.ResponseData
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.information.InformationViewModel
import net.mindzone.robroo.ui.main.information.productCategory.FilterBottomSheetDialog
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class ProductCategory03Fragment : BaseFragment(R.layout.fragment_product_category03), FilterBottomSheetDialog.FilterClickListener {
    private val viewModel by viewModels<InformationViewModel>()
    private lateinit var productCategory03Adapter: ProductCategory03Adapter
    var title = ""
    var checkEmpty = ""
    val azureId = MainActivity.user!!.azureoid
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"ProductCategory03Fragment",0,"")
        val bundle = this.arguments
        val re: ResponseData = bundle?.getSerializable("re3") as ResponseData
        title = bundle.getString("title3")!!
        if (title.length >= 30) {
            title = title.substring(0, 30)+ "...";

        }
        txtTile.text = title
        viewModel.getListProductModel(azureId, re.groupid)

        tvProductCategory03.setOnClickListener {
            val bottomSheetDialogFragment: BottomSheetDialogFragment = FilterBottomSheetDialog.newInstance(re.groupid)
            bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
        }
    }
    private fun filter() {
        productCategory03Adapter.filterList(checkEmpty)
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {
        viewModel.apply {
            listProductModelResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null){
                        setAdapter(it.body()?.responseData!!)
                    }
                    else{
                        rvMenu2ProductCategory03.visibility = View.INVISIBLE
                        layoutNoDataI11.visibility = View.VISIBLE
                    }
                } else {

                }
            }
            status.observe(viewLifecycleOwner){
                when(it){
                    Status.LOADING -> rvMenu2ProductCategory03.veil()
                    Status.SUCCESS -> rvMenu2ProductCategory03.unVeil()
                }


            }
        }
    }

    private fun setAdapter(list: List<net.mindzone.robroo.data.productgroupListproductmodel.entity.ResponseData>) {
        productCategory03Adapter = ProductCategory03Adapter(list,requireActivity())
        rvMenu2ProductCategory03!!.apply {
            setAdapter(productCategory03Adapter)
            setLayoutManager(LinearLayoutManager(context))
            setVeilLayout(R.layout.cell_skeleton)
            addVeiledItems(7)
        }
    }

    override fun onFilterClick(item: String) {
        checkEmpty=item.filter { it.isUpperCase() ||it.isDigit()}
        filter()
        tvProductCategory03.text=item
    }

}
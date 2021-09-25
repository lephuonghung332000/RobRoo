package net.mindzone.robroo.ui.main.information.productInfoWaranty.productCategory02

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_category02.*
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
class ProductCategory02Fragment : BaseFragment(R.layout.fragment_product_category02), FilterBottomSheetDialog.FilterClickListener {
    private val viewModel by viewModels<InformationViewModel>()
    private lateinit var productCategory02Adapter: ProductCategory02Adapter
    var title = ""
    var checkEmpty = ""
    val azureId = MainActivity.user!!.azureoid
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"menu_product_category",0,"")
        val bundle = this.arguments
        val re: ResponseData = bundle?.getSerializable("re2") as ResponseData
        title = bundle.getString("title2")!!
        if (title.length >= 30) {
            title = title.substring(0, 30)+ "...";

        }
        txtTile.text = title
        viewModel.getListProductModel(azureId, re.groupid)

        tvProductCategory02.setOnClickListener {
            val bottomSheetDialogFragment: BottomSheetDialogFragment = FilterBottomSheetDialog.newInstance(re.groupid)
            bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click, "menu_product_warranty",0,title)
        }
    }
    private fun filter() {
        productCategory02Adapter.filterList(checkEmpty)
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
                        rvMenu2ProductCategory02.visibility = View.INVISIBLE
                        layoutNoDataI9.visibility = View.VISIBLE
                    }
                } else {

                }
            }
            status.observe(viewLifecycleOwner){
                when(it){
                    Status.LOADING -> rvMenu2ProductCategory02.veil()
                    Status.SUCCESS -> rvMenu2ProductCategory02.unVeil()
                    else -> {}
                }


            }
        }
    }

    private fun setAdapter(list: List<net.mindzone.robroo.data.productgroupListproductmodel.entity.ResponseData>) {
        productCategory02Adapter = ProductCategory02Adapter(list,requireActivity())
        rvMenu2ProductCategory02!!.apply {
            setAdapter(productCategory02Adapter)
            setLayoutManager(LinearLayoutManager(context))
            setVeilLayout(R.layout.cell_skeleton)
            addVeiledItems(7)
        }
    }

    override fun onFilterClick(item: String) {
        checkEmpty=item.filter { it.isUpperCase() ||it.isDigit()}
        filter()
        tvProductCategory02.text=item
    }


}
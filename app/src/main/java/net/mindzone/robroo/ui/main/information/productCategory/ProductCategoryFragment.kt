package net.mindzone.robroo.ui.main.information.productCategory


import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_category.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.productgroupList.entity.ResponseData
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.information.InformationDetailFragment
import net.mindzone.robroo.ui.main.information.InformationViewModel
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager


@AndroidEntryPoint
class ProductCategoryFragment : BaseFragment(R.layout.fragment_product_category), FilterBottomSheetDialog.FilterClickListener  {
    private val viewModel by viewModels<InformationViewModel>()
    private lateinit var productCategoryAdapter: ProductCategoryAdapter
    var checkEmpty = ""
    val azureId = MainActivity.user!!.azureoid
    var title = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val bundle = this.arguments
    val re: ResponseData = bundle?.getSerializable("re") as ResponseData
    title = bundle.getString("title")!!
    if (title.length >= 30) {
        title = title.substring(0, 30)+ "...";

    }
    txtTile.text = title
    viewModel.getListProductModel(azureId, re.groupid)
    rvMenu2ProductCategory!!.apply {
        setLayoutManager(LinearLayoutManager(context))
        setVeilLayout(R.layout.cell_skeleton)
        addVeiledItems(7)
    }
    tvProductCategory.setOnClickListener {
        val bottomSheetDialogFragment: BottomSheetDialogFragment = FilterBottomSheetDialog.newInstance(re.groupid)
        bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,title)
    }
}
    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }
    private fun filter() {
        productCategoryAdapter.filterList(checkEmpty)
    }
    override fun startObservingValues() {
        viewModel.apply {
            listProductModelResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null){
                        setAdapter(it.body()?.responseData!!)
                    }
                    else{
                        rvMenu2ProductCategory.visibility = View.INVISIBLE
                        layoutNoDataI2.visibility = View.VISIBLE
                    }
                } else {

                }
            }
            status.observe(viewLifecycleOwner){
                when(it){
                    Status.LOADING -> rvMenu2ProductCategory.veil()
                    Status.SUCCESS -> rvMenu2ProductCategory.unVeil()
                    else -> {}
                }


            }
        }
    }

    private fun setAdapter(list: List<net.mindzone.robroo.data.productgroupListproductmodel.entity.ResponseData>) {
        context?.let {
            productCategoryAdapter = ProductCategoryAdapter(list, it, itemListener = {
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
        }
        rvMenu2ProductCategory.setAdapter(productCategoryAdapter)
    }

    override fun onFilterClick(item: String) {
        checkEmpty=item.filter { it.isUpperCase() ||it.isDigit()}
        filter()
        tvProductCategory.text=item
    }
}
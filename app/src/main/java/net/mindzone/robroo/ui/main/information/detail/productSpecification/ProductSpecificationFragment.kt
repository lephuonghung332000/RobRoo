package net.mindzone.robroo.ui.main.group
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_specification.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.productModel.entity.ResponseData
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.information.InformationViewModel
import net.mindzone.robroo.ui.main.information.detail.productSpecification.ProductSpecificationAdapter
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class ProductSpecificationFragment : BaseFragment(R.layout.fragment_product_specification) {
    var azure_id= MainActivity.user?.azureoid!!
    private val viewModel by viewModels<InformationViewModel>()
    private lateinit var productSpecificationAdapter: ProductSpecificationAdapter
    var model_id=""
    fun getModelnameUrlSafe(model: String):String {
        return model.replace(" ", "%20").replace(",", "%2C")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"ProductSpecificationFragment",0,arguments?.getString("id_specification")!!)
        model_id=getModelnameUrlSafe(arguments?.getString("id_specification")!!)
        viewModel.getFields(azure_id,model_id)
        recyclerViewProductSpecification.layoutManager=LinearLayoutManager(context)

    }

    override fun onResume() {
        super.onResume()
        linearLayout.requestLayout()
    }
    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }
    companion object {
        fun newInstance(id: String): Fragment {
            val f = ProductSpecificationFragment()
            val b = Bundle()
            b.putString("id_specification", id)
            f.arguments = b
            return f
        }
    }
    override fun startObservingValues() {
        viewModel.apply {
            fieldsResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null) {
                        setAdapter(it.body()?.responseData!!)
                    }
                    else{
                        layoutNoDataSpecification.visibility=View.VISIBLE
                        recyclerViewProductSpecification.visibility=View.GONE
                    }
                }
            }

        }
    }
    private fun setAdapter(list: List<ResponseData>) {
        productSpecificationAdapter = context?.let { ProductSpecificationAdapter(it, list) }!!
        recyclerViewProductSpecification.setAdapter(productSpecificationAdapter)
    }
}
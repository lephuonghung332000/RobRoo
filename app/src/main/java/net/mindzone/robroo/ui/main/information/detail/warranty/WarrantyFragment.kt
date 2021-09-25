package net.mindzone.robroo.ui.main.group
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import co.lujun.androidtagview.TagContainerLayout
import co.lujun.androidtagview.TagView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_shared_device.*
import kotlinx.android.synthetic.main.fragment_warranty.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.productGetWarranty.entity.ResponseData
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.information.InformationViewModel
import net.mindzone.robroo.ui.main.information.detail.BottomWarrantyFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class WarrantyFragment:BaseFragment(R.layout.fragment_warranty) {

    private val viewModel by viewModels<InformationViewModel>()
    lateinit var mTagContainerLayout:TagContainerLayout
    var azure_id= MainActivity.user?.azureoid!!
    var model_id=""
    fun getModelnameUrlSafe(model: String):String {
        return model.replace(" ", "%20").replace(",", "%2C")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"WarrantyFragment",0,arguments?.getString("id_warranty")!!)
        mTagContainerLayout=view.findViewById(R.id.tagDetail)
        model_id=getModelnameUrlSafe(arguments?.getString("id_warranty")!!)
        viewModel.getWarrantyInfo(azure_id,model_id)

    }
    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun onResume() {
        super.onResume()
        layoutWarranty.requestLayout()
    }
    override fun startObservingValues() {

        viewModel.apply {
            warrantyResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null) {
                        setAdapter(it.body()?.responseData!!)
                    }
                    else {
                        txtWarranty.visibility = View.INVISIBLE
                        layoutPeriod.visibility = View.INVISIBLE
                        layoutDetail.visibility=View.INVISIBLE
                        layoutNoDataWarranty.visibility=View.VISIBLE
                    }
                }
            }
            status.observe(viewLifecycleOwner){
                Log.d("status",status.value.toString())
                when(it){
//                    Status.LOADING->recyclerViewProductSpecification.veil()
//                    Status.SUCCESS->recyclerViewProductSpecification.unVeil()
                }

            }
        }
    }
    companion object {
        fun newInstance(id: String): Fragment {
            val f = WarrantyFragment()
            val b = Bundle()
            b.putString("id_warranty", id)
            f.arguments = b
            return f
        }
    }
    //setup adapter for all recyclerview in fragment
    private fun setAdapter(item: ResponseData){

        txtPeriod.text=item.warranty_period
        var list= listOf(getString(R.string.detail))
        mTagContainerLayout.tags=list
        val typeface = context?.let { ResourcesCompat.getFont(it, R.font.kanit_medium) };
        mTagContainerLayout.tagTypeface=typeface
        mTagContainerLayout.setOnTagClickListener(object : TagView.OnTagClickListener {
            override fun onTagClick(position: Int, text: String) {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click, "menu_warranty",0,txtPeriod.text.toString())
            val webpagerDialogFragment = BottomWarrantyFragment()
            val bundle = Bundle()
            bundle.putString("title9",item.model_code)
            bundle.putString("detail",item.warranty_detail)
            webpagerDialogFragment.arguments = bundle
            webpagerDialogFragment.show(
                requireActivity().supportFragmentManager.beginTransaction(), "BottomSheet" )
            }

            override fun onTagLongClick(position: Int, text: String?) {

            }

            override fun onSelectedTagDrag(position: Int, text: String?) {

            }

            override fun onTagCrossClick(position: Int) {

            }


        })
    }
}

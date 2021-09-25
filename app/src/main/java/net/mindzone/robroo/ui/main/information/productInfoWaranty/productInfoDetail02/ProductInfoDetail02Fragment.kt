package net.mindzone.robroo.ui.main.information.productInfoWaranty.productInfoDetail02

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import co.lujun.androidtagview.TagContainerLayout
import co.lujun.androidtagview.TagView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_info_detail02.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import kotlinx.android.synthetic.main.view_layout_title02.view.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.productInfo.entity.ResponseData
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.information.InformationViewModel
import net.mindzone.robroo.ui.main.information.detail.BottomWarrantyFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class ProductInfoDetail02Fragment: BaseFragment(R.layout.fragment_product_info_detail02) {
    lateinit var mTagContainerLayout: TagContainerLayout
    private val viewModel by viewModels<InformationViewModel>()
    private var title:String=""
    private var model_id=""
    val azureId = MainActivity.user!!.azureoid

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"menu_product_details",0,"")
        title=arguments?.getString("title4")!!
        mTagContainerLayout=view.findViewById(R.id.tagDetail)
        model_id=title
        viewModel.getInfo(azureId,arguments?.getString("title4")!!)
        viewModel.getWarrantyInfo(azureId,arguments?.getString("title4")!!)
        txtTile.text=title
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
            warrantyResponse.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    if (it.body()?.responseData != null) {
                        setAdapter(it.body()?.responseData!!)
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
    }

    private fun setAdapter(item: net.mindzone.robroo.data.productGetWarranty.entity.ResponseData) {
        txtPeriodDetail.text=item.warranty_period
        var list= listOf(getString(R.string.detail))
        mTagContainerLayout.tags=list
        val typeface = context?.let { ResourcesCompat.getFont(it, R.font.kanit_medium) };
        mTagContainerLayout.tagTypeface=typeface
        mTagContainerLayout.setOnTagClickListener(object : TagView.OnTagClickListener {
            override fun onTagClick(position: Int, text: String) {
                val webpagerDialogFragment = BottomWarrantyFragment()
                val bundle = Bundle()
                bundle.putString("title9",item.model_code)
                bundle.putString("detail",item.warranty_detail)
                webpagerDialogFragment.arguments = bundle
                webpagerDialogFragment.show(
                    requireActivity().supportFragmentManager.beginTransaction(), "BottomSheet" )
                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,"menu_product_detail_warranty",0,item.model_code)
            }

            override fun onTagLongClick(position: Int, text: String?) {

            }

            override fun onSelectedTagDrag(position: Int, text: String?) {

            }

            override fun onTagCrossClick(position: Int) {

            }


        })

    }
    private fun addEvent(item: ResponseData) {
        Glide.with(requireContext()).load(item.model_image).into(imgView03)
        layoutTitle02.textViewTitleSeries05.text= item.model_code
        layoutTitle02.textView2.text=item.group_nameth+" "+item.model_prefix

    }

}
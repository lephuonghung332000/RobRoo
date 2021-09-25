package net.mindzone.robroo.ui.main.information

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_information_detail.*
import kotlinx.android.synthetic.main.view_layout_title.*
import kotlinx.android.synthetic.main.view_layout_title.view.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.productInfo.entity.ResponseData
import net.mindzone.robroo.ui.detail.shareddevice.SharedDeviceFragment
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.group.ProductManualFragment
import net.mindzone.robroo.ui.main.group.ProductSpecificationFragment
import net.mindzone.robroo.ui.main.group.WarrantyFragment
import net.mindzone.robroo.ui.viewpager.ImageAdapter
import net.mindzone.robroo.ui.viewpager.ViewPagerAdapter
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.text.Charsets.UTF_8


@AndroidEntryPoint
class InformationDetailFragment : BaseFragment(R.layout.fragment_information_detail) {
    var currentPage = 0
    private val viewModel by viewModels<InformationViewModel>()
    val layoutManager: LinearLayoutManager?= (viewPager2?.getChildAt(0) as? RecyclerView)?.layoutManager as? LinearLayoutManager
     lateinit var adapter: ImageAdapter
    private  lateinit var txtTitle: TextView
    val azureId = MainActivity.user?.azureoid!!
    lateinit var sharedDeviceFragment: SharedDeviceFragment
    private val fragment = ArrayList<Fragment>()
    private var title:String=""
    private var model_id=""
    fun getModelnameUrlSafe(model: String):String {
        return model.replace(" ", "%20").replace(",", "%2C")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"InformationDetailFragment",0,"")
        title=arguments?.getString("model_id")!!
        model_id= title
        model_id=getModelnameUrlSafe(model_id)
        viewModel.getInfo(azureId,getModelnameUrlSafe(model_id))
        txtTitle = view.findViewById(R.id.txtTile)
        txtTitle.text=title
        val warrantyFragment = WarrantyFragment.newInstance(model_id)
        val sharedDeviceFragment = SharedDeviceFragment.newInstance(model_id)
        val productSpecificationFragment = ProductSpecificationFragment.newInstance(model_id)
        val productManualFragment = ProductManualFragment.newInstance(model_id)
        fragment.add(warrantyFragment)
        fragment.add(sharedDeviceFragment)
        fragment.add(productSpecificationFragment)
        fragment.add(productManualFragment)
        viewPager2.adapter = activity?.let { ViewPagerAdapter(it, fragment) }
        TabLayoutMediator(topNavigationView, viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.setText(R.string.warranty_period)
                }
                1 -> {
                    tab.setText(R.string.shared_device)
                }
                2 -> {
                    tab.setText(R.string.product_specification)
                }
                3 -> {
                    tab.setText(R.string.product_manual)
                }
            }
        }.attach()
//        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                super.onPageScrolled(position,positionOffset,positionOffsetPixels)
//                recalculate(position, positionOffset)
//            }
//        })
        //setting slide image
//          viewPager = view.findViewById(R.id.viewPager)

//        adapter = ImageAdapter(emptyList(),context)

//        viewPager.adapter = adapter
////        indicator=view.findViewById(R.id.indicator)
//        indicator.attachViewPager(viewPager)

//        adapter = ImageAdapter(context)
//        viewPager.adapter = adapter
////        indicator=view.findViewById(R.id.indicator)
//        indicator.attachViewPager(viewPager)
        //slide animation
//        viewPager.setPageTransformer(
//            false
//        ) { page, position ->
//            page.alpha = 0f
//            page.visibility = View.VISIBLE
//            // Start Animation for a short period of time
//            page.animate()
//                .alpha(1f).duration =
//                page.resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
//        }


        //slide auto
//        handler = Handler()
//        startAutoSlider(adapter.count);
//        addEvent()
//         timer = object: CountDownTimer(5000,800 ) {
//            override fun onTick(millisUntilFinished: Long) {
//                if (currentPage === adapter.count) {
//                    currentPage = 0
//                }
//                else {
//                    viewPager.setCurrentItem(currentPage++, true)
//                }
//            }
//            override fun onFinish() {
//                timer.start()
//            }
//        }
//        timer.start()

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
    }




    private fun addEvent(item:ResponseData) {
        Glide.with(requireContext())
        .load(item.model_image)
        .error(R.drawable.ic_placeholder_image)
        .into(viewPager)
        layoutTitle.txtNameSeries.text= item.model_code
        if(item.horsepower==null){
            item.horsepower="-"
        }
        layoutTitle.txtHorsePower.text= item.horsepower
        if(item.price==0){
            layoutTitle.txtPrice.text="-"
        }
        else{
            layoutTitle.txtPrice.text= item.price.toString()
        }
        txtgroup.text=item.group_nameth+" "+item.model_prefix
    }

}
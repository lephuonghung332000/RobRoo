package net.mindzone.robroo.ui.main.information.productSharedDevice.productDetailFinal

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.viewpagerdots.DotsIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_detail_final.*
import kotlinx.android.synthetic.main.view_layout_title.*
import kotlinx.android.synthetic.main.view_layout_title02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.ui.viewpager.ImageAdapter
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager

@AndroidEntryPoint
class ProductDetailFinalFragment: BaseFragment(R.layout.fragment_product_detail_final) {
    private  lateinit var txtTitle: TextView
    private lateinit var  txtName: String
    var currentPosition = 0

    private lateinit var adapter: ImageAdapter
    private lateinit var dotsIndicator: DotsIndicator


    private lateinit var productDetailFinalAdapter: ProductDetailFinalAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"ProductDetailFinalFragment",0,"")
        txtName = arguments?.getString("content")!!
        txtNameSeries.text = txtName
        textView2.text="อุปกรณ์ ที* ใช้ ร่ วมกัน - จอบหมุน\n"
        setAdapter()
        txtTitle = view.findViewById(R.id.txtTile)
        txtTitle.text = getString(R.string.shared_device)
        adapter= ImageAdapter(emptyList(),context)
        viewPagerFinal.adapter = adapter
        dotsIndicator=view.findViewById(R.id.indicator)
        dotsIndicator.attachViewPager(viewPagerFinal)

    }
    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {

    }
    private fun setAdapter(){
        productDetailFinalAdapter = ProductDetailFinalAdapter()
        rvProductDetailFinal!!.apply {
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = productDetailFinalAdapter
        }
    }
}
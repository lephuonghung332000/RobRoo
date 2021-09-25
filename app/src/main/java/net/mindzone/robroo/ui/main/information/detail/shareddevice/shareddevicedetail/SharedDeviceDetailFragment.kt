package net.mindzone.robroo.ui.main.information.detail.shareddevice.shareddevicedetail

import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_information_detail.*
import kotlinx.android.synthetic.main.fragment_service_detail.*
import kotlinx.android.synthetic.main.fragment_shared_device_detail.*
import kotlinx.android.synthetic.main.fragment_shared_device_detail.layoutTitle
import kotlinx.android.synthetic.main.item_info_dowloaded.view.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.view.*
import kotlinx.android.synthetic.main.view_layout_title.view.*
import kotlinx.android.synthetic.main.view_layout_title02.view.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.ui.main.information.detail.shareddevice.SharedDeviceDetailAdapter
import net.mindzone.robroo.ui.viewpager.ImageAdapter
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager
import java.util.*


@AndroidEntryPoint
class SharedDeviceDetailFragment : BaseFragment(R.layout.fragment_shared_device_detail) {
    private lateinit var sharedDeviceDetailAdapter: SharedDeviceDetailAdapter

    val DELAY_MS: Long = 500 //delay in milliseconds before task is to be executed
    private var title:String=""
    private var runnable: Runnable? = null
    private lateinit var handler: Handler
    var lastPosition = 0
    var currentPosition = 0

    private lateinit var adapter: ImageAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"SharedDeviceDetailFragment",0,"")
        title=arguments?.getString("content")!!
        layoutTitle.txtNameSeries.text=title
        layoutTitle.textView2.text="อุปกรณ์ ที* ใช้ ร่ วมกัน - จอบหมุน\n"
        sharedDeviceDetailAdapter = context?.let { SharedDeviceDetailAdapter() }!!
        recyclerShareDeviceDetail!!.apply {
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = sharedDeviceDetailAdapter
        }
        adapter= ImageAdapter(emptyList(),context)
        viewPagerDetail.adapter = adapter
        indicatorDetail.attachViewPager(viewPagerDetail)
        //slide animation
        viewPagerDetail.setPageTransformer(
            false
        ) { page, position ->
            page.alpha = 0f
            page.visibility = View.VISIBLE
            // Start Animation for a short period of time
            page.animate()
                .alpha(1f).duration =
                page.resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        }
        handler = Handler()
        startAutoSlider(adapter.count)
        addEvent()

    }
    override fun onStop() {
        super.onStop()
        runnable?.let { handler!!.removeCallbacks(it) };
    }
    private fun addEvent() {
        viewPagerDetail.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                currentPosition = position
            }
            override fun onPageSelected(position: Int) {
                viewPagerDetail.setCurrentItem(position,true)
            }
            override fun onPageScrollStateChanged(state: Int) {
                viewPagerDetail.setOnTouchListener(object : View.OnTouchListener {
                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                        when (event?.action) {
                            MotionEvent.ACTION_DOWN ->runnable?.let { handler!!.removeCallbacks(it)}
                        }

                        return v?.onTouchEvent(event) ?: true
                    }
                })
                if (state == 0) {
                    if (currentPosition == lastPosition) {
                        if (currentPosition == adapter.count - 1) {
                            viewPagerDetail.setCurrentItem(0);
                        } else if (currentPosition == 0) {
                            viewPagerDetail.setCurrentItem(adapter.count - 1);
                        }
                    } else {
                        lastPosition = currentPosition;
                    }
                }
            }
        })
    }

    private fun startAutoSlider(count: Int) {

        runnable = Runnable {
            var pos: Int = viewPagerDetail.getCurrentItem()
            pos = pos + 1
            if (pos >= count) pos = 0
            viewPagerDetail.setCurrentItem(pos)
            runnable?.let { handler?.postDelayed(it, 3000) }
        }
        handler?.postDelayed(runnable!!, 3000)
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {
    }
    override fun startObservingValues() {
    }

}
package net.mindzone.robroo.ui.main.service

import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_service_detail.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.ui.main.idea.knowledge.detail.BottomWebDialog
import net.mindzone.robroo.ui.viewpager.ImageAdapter
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager
import java.util.*


@AndroidEntryPoint
class ServiceDetailFragment : BaseFragment(R.layout.fragment_service_detail) {
    //    lateinit var txtTile: TextView
    var currentPage = 0
    var timer: Timer? = null
    private var runnable: Runnable? = null
    private lateinit var handler: Handler
    var lastPosition = 0
    var currentPosition = 0


    val DELAY_MS: Long = 500 //delay in milliseconds before task is to be executed
    val PERIOD_MS: Long = 4000 // time in milliseconds between successive task executions.
    private lateinit var adapter: ImageAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"ServiceDetailFragment",0,"")
        txtTile.setText(getString(R.string.drone_service))
        txtTile.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            getResources().getDimension(R.dimen.dp_18)
        )
        val typeface = context?.let { ResourcesCompat.getFont(it, R.font.kanit_semibold) }
        txtTile.setTypeface(typeface)
        adapter = ImageAdapter(emptyList(),context)
        viewPagerService.adapter = adapter
        indicatorService.attachViewPager(viewPagerService)
        txtContentService.setAnimationDuration(1000L);
        txtContentService.setInterpolator(OvershootInterpolator())
        txtContentService.expandInterpolator = OvershootInterpolator()
        txtContentService.collapseInterpolator = OvershootInterpolator()
        btnExpand.setOnClickListener {
            val bottomSheetDialog = BottomWebDialog()
            bottomSheetDialog.show(
                requireActivity().supportFragmentManager.beginTransaction(),
                "BottomSheet"
            )
        }

        viewPagerService.adapter = adapter
        indicatorService.attachViewPager(viewPagerService)
        //slide animation
        viewPagerService.setPageTransformer(
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

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {
    }

    override fun startObservingValues() {
    }

    override fun onStop() {
        super.onStop()
        runnable?.let { handler!!.removeCallbacks(it) };
    }

    private fun addEvent() {
        viewPagerService.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                currentPosition = position
            }

            override fun onPageSelected(position: Int) {
                viewPagerService.setCurrentItem(position, true)
            }

            override fun onPageScrollStateChanged(state: Int) {
                viewPagerService.setOnTouchListener(object : View.OnTouchListener {
                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                        when (event?.action) {
                            MotionEvent.ACTION_DOWN -> runnable?.let { handler!!.removeCallbacks(it) }
                        }

                        return v?.onTouchEvent(event) ?: true
                    }
                })
                if (state == 0) {
                    if (currentPosition == lastPosition) {
                        if (currentPosition == adapter.count - 1) {
                            viewPagerService.setCurrentItem(0);
                        } else if (currentPosition == 0) {
                            viewPagerService.setCurrentItem(adapter.count - 1);
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
            var pos: Int = viewPagerService.getCurrentItem()
            pos = pos + 1
            if (pos >= count) pos = 0
            viewPagerService.setCurrentItem(pos)
            runnable?.let { handler?.postDelayed(it, 3000) }
        }
        handler?.postDelayed(runnable!!, 3000)
    }

}
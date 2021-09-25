package net.mindzone.robroo.ui.main.generalMenu.applicationManual.applicationManualDetail

import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.afollestad.viewpagerdots.DotsIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home_side_application_manual_detail.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.faqList.entity.FaqList
import net.mindzone.robroo.ui.viewpager.ImageAdapter
import net.mindzone.robroo.utils.AuditType
import net.mindzone.robroo.utils.extensions.Status
import net.mindzone.sampleaudit.AuditManager
import java.util.*

@AndroidEntryPoint
class HomeSideMenuApplicationManualDetailFragment () : BaseFragment(R.layout.fragment_home_side_application_manual_detail){
    private val viewModel by viewModels<HomeSideMenuApplicationManualDetailViewModel>()

    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 500 //delay in milliseconds before task is to be executed
    val PERIOD_MS: Long = 4000 // time in milliseconds between successive task executions.

    private lateinit var viewPager: ViewPager
    private lateinit var adapter: ImageAdapter
    private lateinit var dotsIndicator: DotsIndicator
    private lateinit var expTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = this.arguments
        val faqList : FaqList = bundle?.getSerializable("data") as FaqList

        viewModel.getFaqArticle(faqList.faq_id)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"faq_manual_details",faqList.faq_id.toInt(),faqList.title)
        return super.onCreateView(inflater, container, savedInstanceState)


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtTile?.let { it.text = getString(R.string.application_manual) }

        expTextView= view.findViewById(R.id.tvContent)
        viewPager = view.findViewById(R.id.viewPagerMenu5)

        dotsIndicator=view.findViewById(R.id.indicator)



    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {
    }
    override fun startObservingValues() {

        viewModel.apply {
            faqListResponse.observe(viewLifecycleOwner) {
                txtTitle.text = it.title
                tvDate.text = it.date
                tvContent.text = Html.fromHtml(it.desc)
                adapter= ImageAdapter(it.images,context)
                viewPager.adapter = adapter
                dotsIndicator.attachViewPager(viewPager)
                slideAnimationAndAuto()
            }
            status.observe(viewLifecycleOwner){
                when(it){
                    Status.LOADING -> veilLayout.veil()
                    Status.SUCCESS -> veilLayout.unVeil()
                }
            }
        }
    }

    private fun slideAnimationAndAuto(){
        //slide animation
        viewPager.setPageTransformer(
            false
        ) { page, position ->
            page.alpha = 0f
            page.visibility = View.VISIBLE
            // Start Animation for a short period of time
            page.animate()
                .alpha(1f).duration =
                page.resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        }
        //slide auto
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == adapter.count) {
                currentPage = 0
            }
            viewPager.setCurrentItem(currentPage++, true)
        }
        timer = Timer() // This will create a new Thread
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, DELAY_MS, PERIOD_MS)
    }
}
package net.mindzone.robroo.ui.main.information

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_manual_and_pdf_dowloadedragment.*
import kotlinx.android.synthetic.main.fragment_product_manual_parent.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.manualDocument.entity.listDocument.Items
import net.mindzone.robroo.ui.main.information.pdfDowloadedParent.PdfDowloadedParentFragment
import net.mindzone.robroo.ui.main.information.productManualParent.ProductManualParentFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager
import java.util.*


@AndroidEntryPoint
class  ManualAndPdfDowloadedFragment : BaseFragment(R.layout.fragment_manual_and_pdf_dowloadedragment){
    private val viewModel by viewModels<InformationViewModel>()
    var tabLayout: TabLayout? = null
//    var liveData = MutableLiveData< List<Items>>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"ManualAndPdfDowloadedFragment",0,"")
        tabLayout = view.findViewById(R.id.tabLayoutMenu2)
        txtTile?.let { it.text = getString(R.string.infomation_manual) }
        setUpViewPagerAndTabLayout()

    }

    private fun setUpViewPagerAndTabLayout(){
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.addFragment(ProductManualParentFragment(), getString(R.string.list_all))
        viewPagerAdapter.addFragment(PdfDowloadedParentFragment(), getString(R.string.dowloaded))
        viewPagerMenu2.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPagerMenu2.adapter = viewPagerAdapter
        tabLayoutMenu2.setupWithViewPager(viewPagerMenu2)
        viewPagerMenu2.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPagerMenu2!!.currentItem = tab.position
                if (tab.position == 0) {
                    AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,tab.position,txtTile.text.toString())
                    cardViewSearch!!.apply {
                        cardViewSearch.visibility = View.VISIBLE
                    }
                } else {
                    AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,tab.position,txtTile.text.toString())
                    cardViewSearch!!.apply {
                        cardViewSearch.visibility = View.GONE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {


    }


    inner class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager){
        private val fragment  = ArrayList<Fragment>()
        private val title  = ArrayList<String>()
        fun update(list: List<Items>){
            notifyDataSetChanged()
        }
        override fun getItem(position: Int): Fragment {
            return fragment[position]
        }

        override fun getCount(): Int {
            return fragment.size
        }

        fun addFragment(fragment: Fragment, title: String){
            this.fragment.add(fragment)
            this.title.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return title[position]
        }

    }

}
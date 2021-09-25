package net.mindzone.robroo.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import net.mindzone.robroo.ui.main.home.HomeFragment
import net.mindzone.robroo.ui.main.idea.IdeaFragment
import net.mindzone.robroo.ui.main.information.InformationDetailFragment
import net.mindzone.robroo.ui.main.information.InformationFragment
import net.mindzone.robroo.ui.main.information.productInfo.ProductInfoFragment
import net.mindzone.robroo.ui.main.service.ServiceFragment
import net.mindzone.robroo.ui.main.share.mainMenu4.Menu4MainFragment

class MainViewPagerAdapter(fm: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fm, behavior) {

    private val fragment = ArrayList<Fragment>()
    init {
        fragment.add(HomeFragment())
        fragment.add(InformationFragment())
        fragment.add(ServiceFragment())
        fragment.add(Menu4MainFragment())
        fragment.add(IdeaFragment())
    }
    override fun getCount(): Int {
        return fragment.size
    }

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }
}
package net.mindzone.robroo.ui.main.information

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_infomation.*
import kotlinx.android.synthetic.main.fragment_service.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.ui.main.information.productInfo.ProductInfoFragment
import net.mindzone.robroo.ui.main.information.productInfoWaranty.InfoWarantyFragment
import net.mindzone.robroo.ui.main.information.productSharedDevice.InfoSharedDeviceFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager
import java.util.*

@AndroidEntryPoint
class InformationFragment : BaseFragment(R.layout.fragment_infomation) {
    private val viewModel by viewModels<InformationViewModel>()
    private lateinit var informationAdapter: InformationAdapter
    var list = ArrayList<String>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"info_menu",0,"")
        list.clear()
        list.add(getString(R.string.infomation_product))
        list.add(getString(R.string.infomation_manual))
        list.add(getString(R.string.infomation_waranty))
        list.add(getString(R.string.infomation_shared_device))
        rvMenu2Home.setLayoutManager(LinearLayoutManager(context))
        requireActivity()?.let {
            informationAdapter = InformationAdapter(list, it) {
                if (it.equals(getString(R.string.infomation_product))){
                    val createFragment = ProductInfoFragment()
                    fragmentManager?.beginTransaction() ?.setCustomAnimations(
                            R.anim.slide_in_from_right,
                            R.anim.slide_out_to_left,
                            R.anim.slide_in_from_left,
                            R.anim.slide_out_to_right
                    //animation
                    )
                        ?.replace(R.id.frameMain, createFragment)?.addToBackStack(null)?.commit()
            }
                if (it.equals(getString(R.string.infomation_manual))){
                val createFragment = ManualAndPdfDowloadedFragment()
                fragmentManager?.beginTransaction() ?.setCustomAnimations(
                        R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left,
                        R.anim.slide_in_from_left,
                        R.anim.slide_out_to_right
                )
                    ?.replace(R.id.frameMain, createFragment)?.addToBackStack(null)?.commit()

            }
                if (it.equals(getString(R.string.infomation_waranty))){
                val createFragment = InfoWarantyFragment()
                fragmentManager?.beginTransaction() ?.setCustomAnimations(
                        R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left,
                        R.anim.slide_in_from_left,
                        R.anim.slide_out_to_right
                )
                    ?.replace(R.id.frameMain, createFragment)?.addToBackStack(null)?.commit()
            }
                else if (it.equals(getString(R.string.infomation_shared_device))){
                    val createFragment = InfoSharedDeviceFragment()
                    fragmentManager?.beginTransaction() ?.setCustomAnimations(
                            R.anim.slide_in_from_right,
                            R.anim.slide_out_to_left,
                            R.anim.slide_in_from_left,
                            R.anim.slide_out_to_right
                    )
                        ?.replace(R.id.frameMain, createFragment)?.addToBackStack(null)?.commit()
                }
            }

            rvMenu2Home.run{
                setVeilLayout(R.layout.cell_skeleton)
                setAdapter(informationAdapter)
                addVeiledItems(15)
                unVeil()
            }

        }
    }
    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }
    override fun startObservingValues() {

    }

}

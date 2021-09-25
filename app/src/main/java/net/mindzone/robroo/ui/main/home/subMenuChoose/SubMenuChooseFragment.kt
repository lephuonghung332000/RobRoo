package net.mindzone.robroo.ui.main.home.subMenuChoose

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_sub_menu_choose.*
import kotlinx.android.synthetic.main.view_custom_toolbar02.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.subMenu.entity.SubMenu
import net.mindzone.robroo.databinding.FragmentSubMenuChooseBinding
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.home.touchhelper.MyItemTouchHelperCallBack
import net.mindzone.robroo.ui.main.home.touchhelper.OnStartDragListener
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager


@AndroidEntryPoint
class SubMenuChooseFragment : BaseFragment(R.layout.fragment_sub_menu_choose){

    private val viewModel by viewModels<SubMenuChooseViewModel>()
    private lateinit var subMenuChooseAdapter: SubMenuChooseAdapter
    private var listSubMenu: List<SubMenu>? = mutableListOf()
    var itemTouchHelper: ItemTouchHelper? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"firstpage_add_widget",0,"")

        txtTile?.let{
            it.text = "เพิ่มเมนูย่อย"
        }
        subMenuChooseAdapter = SubMenuChooseAdapter(viewModel, object: OnStartDragListener{
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper!!.startDrag(viewHolder)
            }

        })
        subMenuChooseAdapter.setHasStableIds(true)
        rvSubMenuChoose.apply{
            adapter = subMenuChooseAdapter
        }
        val callback = MyItemTouchHelperCallBack(subMenuChooseAdapter)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper!!.attachToRecyclerView(rvSubMenuChoose)
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {
        (viewDataBinding as FragmentSubMenuChooseBinding).apply {
            fragmentViewModel = viewModel
        }
    }

    override fun startObservingValues() {
        viewModel.apply {
            allMenus.observe(viewLifecycleOwner) {
                subMenuChooseAdapter.submitList(it)
                subMenuChooseAdapter.notifyDataSetChanged()
            }
        }
//
    }

}

package net.mindzone.robroo.ui.main.home

import DroneServiceFragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.base.BaseFragment
import net.mindzone.robroo.data.forumArticle.entity.forumList.ForumGroups
import net.mindzone.robroo.data.subMenu.entity.SubMenu
import net.mindzone.robroo.ui.main.MainActivity
import net.mindzone.robroo.ui.main.home.subMenuChoose.SubMenuChooseFragment
import net.mindzone.robroo.ui.main.home.touchhelper.MyItemTouchHelperCallBack
import net.mindzone.robroo.ui.main.home.touchhelper.OnStartDragListener
import net.mindzone.robroo.ui.main.idea.knowledge.AllAndUnreadKnowledgeFragment
import net.mindzone.robroo.ui.main.information.ManualAndPdfDowloadedFragment
import net.mindzone.robroo.ui.main.information.detail.BottomWarrantyFragment
import net.mindzone.robroo.ui.main.information.pdfDowloadedParent.PdfDowloadedParentFragment
import net.mindzone.robroo.ui.main.information.productInfo.ProductInfoFragment
import net.mindzone.robroo.ui.main.information.productInfoWaranty.InfoWarantyFragment
import net.mindzone.robroo.ui.main.information.productSharedDevice.InfoSharedDeviceFragment
import net.mindzone.robroo.ui.main.service.kpdaService.KPDAServiceFragment
import net.mindzone.robroo.ui.main.share.listShare.ShareFragment
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var subMenuAdapter: SubMenuAdapter

    lateinit var listSubmenu : MutableList<SubMenu>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.screen,"firstpage_menu",0,"")

        setEvent()
        buildRecyclerView()
        searchView.addTextChangeListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())

                AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,"firstpage_search",0,s.toString())

            }
        })
    }

    private fun filter(text: String) {
        val filterList : MutableList<SubMenu> = mutableListOf()
//        listSubmenu.forEach {
//            if(it.title.toLowerCase().contains(text.toLowerCase())){
//                filterList.add(it)
//            }
//        }
        subMenuAdapter.filterList(filterList)

    }

    private fun buildRecyclerView() {
        subMenuAdapter = SubMenuAdapter(viewModel,requireContext(), object : SubMenuAdapter.IClickListener{
            override fun itemClick(subMenu: SubMenu) {
//                when(subMenu.subMenuId){
//                    1 -> openDiaLog("https://kpad.kubota.com/login/consumer?consumer_code=504eiy6Baez")
//                    2 -> openFragment(ManualAndPdfDowloadedFragment())
//                    3 ->openFragment(ProductInfoFragment())
//                    4 -> openFragment(InfoSharedDeviceFragment())
//                    5 -> openFragment(InfoWarantyFragment())
//                    6 -> sendDataAndOpenFragment(ForumGroups("1", title = ""),ShareFragment())
////                        openFragment(DroneServiceFragment())
//                    7 -> sendStringAndIntData("tittle5","Flash knowledge", "id5",1,AllAndUnreadKnowledgeFragment())
//                    8-> openDiaLog("https://www.siamkubota.co.th/product-recommendation")
//                    9 -> openDiaLog(" https://sites.google.com/view/droneserviceapp/%E0%B8%AB%E0%B8%99%E0%B8%B2%E0%B9%81%E0%B8%A3%E0%B8%81?authuser=2")
//                    10 -> sendStringAndIntData("kpda","11","titleService","รอบรู้เกษตร", KPDAServiceFragment())
//                    11 -> sendStringAndIntData("kpda","15","titleService","โดรน", KPDAServiceFragment())
//                    12 -> sendStringAndIntData("kpda","84","titleService","งานบริการ", KPDAServiceFragment())
//                    13 -> sendStringAndIntData("kpda","80","titleService","โรงเรือนอัจฉริยะ", KPDAServiceFragment())
//                    14 -> openDiaLog("https://forms.gle/5iScfjccJ416q9E99")
//                    15 -> sendDataAndOpenFragment(ForumGroups("2", title = ""),ShareFragment())
//                    16 -> sendStringAndIntData("tittle5","Service Beyond Hub Podcast", "id5",9,AllAndUnreadKnowledgeFragment())
//                    17 -> sendStringAndIntData("tittle5","CS Innovative Talk", "id5",6,AllAndUnreadKnowledgeFragment())
//                    18 -> sendStringAndIntData("tittle5","แบ่งปันความรู้เทคนิค", "id5",7,AllAndUnreadKnowledgeFragment())
//                }
            }


        })
        subMenuAdapter.setHasStableIds(true)
        rvListAddSubMenu.apply {
            layoutManager = GridLayoutManager(context,3)
            adapter = subMenuAdapter
        }
//        val callback = MyItemTouchHelperCallBack(subMenuAdapter)
//        itemTouchHelper = ItemTouchHelper(callback)
//        itemTouchHelper!!.attachToRecyclerView(rvListAddSubMenu)
    }

    override fun setViewModel(viewDataBinding: ViewDataBinding?) {

    }

    override fun startObservingValues() {


        viewModel.apply {
            getSubMenuApi(MainActivity.user!!.azureoid)

            allSubMenuApiResponse.observe(viewLifecycleOwner){ its ->
                if (allMenus.value?.size != its.body()?.responseData?.subMenu!!.size) {
                    addDataToRoom()
                }
               Log.d("asdfasdfasdfadf", allMenus.value?.size.toString())
               Log.d("asdfasdfasdfadf ","api" + its.body()?.responseData?.subMenu!!.size.toString())

//                var listC =  allSubMenuApiResponse.value?.body()?.responseData?.subMenu?.filter {i->
//                    i !in allMenus.value?.map { item -> item }
//                }
//                var listA = listOf(1,2,3,4)
//                var listB=listOf(3,4)
//                var lisC=listA.filter { it !in listB.map{item->item}}
            }



//          addDataToRoom()
            subMenusIsChecked.observe(viewLifecycleOwner){
                listSubmenu = it
                checkListNullOrEmpty(it)
                subMenuAdapter.submitList(it)
                subMenuAdapter.notifyDataSetChanged()
                Log.d("listSubMenu", "size is checked :" + it.size)
            }

        }
    }

    fun checkSaveData(subMenu: SubMenu) {

    }
    private fun checkListNullOrEmpty(list: MutableList<SubMenu>) {

        if (list.isEmpty()){
            rvListAddSubMenu.visibility = View.GONE
            layoutNotYetAddSubMenu.visibility = View.VISIBLE
        }else {
            layoutNotYetAddSubMenu.visibility = View.GONE
            rvListAddSubMenu.visibility = View.VISIBLE

        }
    }
    private fun setEvent (){

        btAddSubMenu.setOnClickListener {

            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,this::class.java.simpleName,0,this::class.qualifiedName)

            val newFragment = SubMenuChooseFragment()
            val transaction = fragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_right, R.anim.slide_out_to_right)
            transaction?.replace(R.id.frameMain, newFragment)
            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction?.addToBackStack(null)
            transaction?.commit()

            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,"firstpage_add",0,"")
        }
    }
    fun sendStringAndIntData(keyString: String,string: String,keyInt: String, int: Int, fragment: Fragment){
        val bundle = Bundle()
        bundle.putString(keyString, string)
        bundle.putInt(keyInt, int)
        fragment.arguments = bundle
        openFragment(fragment)
    }
    fun sendDataAndOpenFragment(forumGroups : ForumGroups, fragment : Fragment){
        val bundle = Bundle()
        bundle.putSerializable(ShareFragment.EXTRA_FORUM_GROUP, forumGroups)
        fragment.arguments = bundle
        openFragment(fragment)
    }

    private fun openFragment(fragment : Fragment){
        val transaction = fragmentManager?.beginTransaction()
            ?.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_right, R.anim.slide_out_to_right)
        transaction?.replace(R.id.frameMain, fragment)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
    fun openDiaLog (string: String){
        val webpagerDialogFragment = BottomWarrantyFragment()
        val bundle = Bundle()
        bundle.putString("linkService",string.toString())
        webpagerDialogFragment.arguments = bundle
        webpagerDialogFragment.show(
            requireActivity().supportFragmentManager.beginTransaction(), "BottomSheet"
        )
    }
    fun sendStringAndIntData(keyString: String,string: String,keyInt: String, int: String, fragment: Fragment){
        val bundle = Bundle()
        bundle.putString(keyString, string)
        bundle.putString(keyInt, int)
        fragment.arguments = bundle
        openFragment(fragment)
    }


}
package net.mindzone.robroo.ui.main.home.subMenuChoose

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.mindzone.robroo.R
import net.mindzone.robroo.RobRooApplication
import net.mindzone.robroo.data.subMenu.entity.SubMenu
import net.mindzone.robroo.data.subMenu.entity.SubMenuResponse
import net.mindzone.robroo.data.subMenu.repository.SubMenuRepository
import net.mindzone.robroo.utils.AuditType
import net.mindzone.sampleaudit.AuditManager
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SubMenuChooseViewModel @Inject constructor(
    private val subMenuRepository: SubMenuRepository
) : ViewModel() {
    private var fakeListSubMenu: MutableList<SubMenu> = mutableListOf()
    private val subMenusIsChecked: LiveData<MutableList<SubMenu>> =
        subMenuRepository.getAllSelectedMenus()
    val allMenus: LiveData<MutableList<SubMenu>> = subMenuRepository.getAllSubMenu()
    val subMenuSelected = MediatorLiveData<List<SubMenu>>()

    init {
        subMenuSelected.addSource(allMenus) { list ->
            subMenuSelected.value = list.filter { it.selected }.sortedBy { it.position }
        }
    }


    private var _allSubMenuApiRespone = MutableLiveData<Response<SubMenuResponse>>()
    val allSubMenuApiResponse: MutableLiveData<Response<SubMenuResponse>> = _allSubMenuApiRespone


    fun getSubMenuApi (azureId : String){
        viewModelScope.launch {
            val response = subMenuRepository.getSubMenuApi(azureId)
            _allSubMenuApiRespone.value = response
        }
    }
    fun onSubMenuClick(subMenu: SubMenu) {
        val itemsToUpdate = mutableListOf<SubMenu>()
        allMenus?.value?.filter { it.selected }?.let {
            Log.d("subMenusIsChecewmodel", it.size.toString())
            itemsToUpdate.addAll(it)
        }
        subMenu.selected = !subMenu.selected
        if (subMenu.selected)
        {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,"firstpage_widget_add",0,subMenu.titleth)
        } else {
            AuditManager.instance.track(RobRooApplication.applicationContext(), AuditType.click,"firstpage_widget_remove",0,subMenu.titleth)
        }

        if (subMenu.selected) {
            itemsToUpdate.add(subMenu)
        }
//        updatePosition(itemsToUpdate)
        Log.d("size", itemsToUpdate.size.toString())
        // Todo: Update database (all #itemsToUpdate)
        viewModelScope.launch {
            subMenuRepository.update(
                SubMenu(
                    uniqueid = subMenu.uniqueid,
                    titleth = subMenu.titleth,
                    type = subMenu.type,
                    selected = subMenu.selected,
                    image = subMenu.image,
                    image2 = subMenu.image2,
                    position = itemsToUpdate.size,
                    positionAllSubMenu = subMenu.positionAllSubMenu
                )
            )
//            subMenuRepository.getAllSelectedMenus()
        }
    }
    fun updateSubMenu(subMenu: SubMenu) = viewModelScope.launch { subMenuRepository.update(subMenu) }
     fun updatePosition(items: List<SubMenu>) {
        items.forEachIndexed { index, item ->
            item.positionAllSubMenu = index + 1

        }

    }


}
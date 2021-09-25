package net.mindzone.robroo.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.mindzone.robroo.R
import net.mindzone.robroo.base.BaseFragmentVM
import net.mindzone.robroo.data.faqList.entity.FaqList
import net.mindzone.robroo.data.subMenu.entity.SubMenu
import net.mindzone.robroo.data.subMenu.entity.SubMenuResponse
import net.mindzone.robroo.data.subMenu.repository.SubMenuRepository
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val subMenuRepository: SubMenuRepository) :
    BaseFragmentVM() {
    val allMenus: LiveData<MutableList<SubMenu>> = subMenuRepository.getAllSubMenu()
    private var fakeListSubMenu: MutableList<SubMenu> = mutableListOf()



    fun addDataToRoom() {
        viewModelScope.launch {
            allSubMenuApiResponse.value?.body()?.responseData?.subMenu!!.toMutableList().forEach {
                var subMenu = it
                if(subMenu.defaultSelect ==1 ){
                    subMenu.selected = true
                    subMenu.toString()
                    subMenuRepository.insert(subMenu)
                }
                subMenuRepository.insert(subMenu)
            }



//            if (allMenus.value.isNullOrEmpty() || allMenus.value!!.size != 18) {
//                fakeListSubMenu.forEach {
//                    subMenuRepository.insert(it)
//                }
//            }
        }

    }

    val subMenusIsChecked: LiveData<MutableList<SubMenu>> = subMenuRepository.getAllSelectedMenus()
    fun updateSubMenu(subMenu: SubMenu) = viewModelScope.launch { subMenuRepository.update(subMenu) }



    private var _allSubMenuApiRespone = MutableLiveData<Response<SubMenuResponse>>()
    val allSubMenuApiResponse: MutableLiveData<Response<SubMenuResponse>> = _allSubMenuApiRespone


    fun getSubMenuApi (azureId : String){
        viewModelScope.launch {
            val response = subMenuRepository.getSubMenuApi(azureId)
            _allSubMenuApiRespone.value = response
        }
    }
    }
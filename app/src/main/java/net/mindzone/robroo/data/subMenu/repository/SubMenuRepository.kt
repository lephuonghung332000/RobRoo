package net.mindzone.robroo.data.subMenu.repository

import androidx.lifecycle.LiveData
import net.mindzone.robroo.data.subMenu.local.SubMenuDAO
import net.mindzone.robroo.data.subMenu.entity.SubMenu
import net.mindzone.robroo.data.subMenu.entity.SubMenuResponse
import retrofit2.Response

interface SubMenuRepository {
    suspend fun insert (subMenu: SubMenu) : Long
    suspend fun update (subMenu: SubMenu)
    suspend fun update (items:MutableList<SubMenu>)
    suspend fun delete (subMenu: SubMenu)
    fun getAllSubMenu (): LiveData<MutableList<SubMenu>>
    fun getAllSelectedMenus (): LiveData<MutableList<SubMenu>>

    suspend fun getSubMenuApi(azureId : String) : Response<SubMenuResponse>

}
package net.mindzone.robroo.data.subMenu.local

import androidx.lifecycle.LiveData
import net.mindzone.robroo.data.subMenu.entity.SubMenu
import retrofit2.Response

interface SubMenuDataSource  {
    suspend fun insert(subMenu: SubMenu): Long
    suspend fun update (subMenu: SubMenu)
    suspend fun update (items: List<SubMenu>)
    suspend fun delete (subMenu: SubMenu)
    fun getAllSubMenu (): LiveData<MutableList<SubMenu>>
    fun getAllSelectedMenus (): LiveData<MutableList<SubMenu>>

}
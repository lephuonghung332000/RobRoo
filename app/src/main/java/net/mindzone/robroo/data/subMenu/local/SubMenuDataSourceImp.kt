package net.mindzone.robroo.data.subMenu.local

import androidx.lifecycle.LiveData
import net.mindzone.robroo.data.subMenu.entity.SubMenu

class SubMenuDataSourceImp internal constructor(private val subMenuDAO: SubMenuDAO): SubMenuDataSource {
    override suspend fun insert(subMenu: SubMenu): Long {
        return subMenuDAO.insert(subMenu)
    }


    override suspend fun update(subMenu: SubMenu) {
        return subMenuDAO.update(subMenu)
    }

    override suspend fun update(items: List<SubMenu>) {
       subMenuDAO.update(items)
    }

    override suspend fun delete(subMenu: SubMenu) {
        return subMenuDAO.delete(subMenu)
    }

    override fun getAllSubMenu(): LiveData<MutableList<SubMenu>> {
       return subMenuDAO.getAllSubMenu()
    }

    override fun getAllSelectedMenus(): LiveData<MutableList<SubMenu>> {
        return subMenuDAO.getSubMenuIsChecked()
    }
}
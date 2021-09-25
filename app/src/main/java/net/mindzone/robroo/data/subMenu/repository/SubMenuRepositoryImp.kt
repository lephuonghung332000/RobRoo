package net.mindzone.robroo.data.subMenu.repository

import androidx.lifecycle.LiveData
import net.mindzone.robroo.data.subMenu.entity.SubMenu
import net.mindzone.robroo.data.subMenu.entity.SubMenuResponse
import net.mindzone.robroo.data.subMenu.local.SubMenuDataSource
import net.mindzone.robroo.data.subMenu.remote.RemoteSubMenuDataSource
import retrofit2.Response

class SubMenuRepositoryImp(private val dataSource: SubMenuDataSource,val remoteSubMenuDataSource: RemoteSubMenuDataSource) : SubMenuRepository {

    override suspend fun insert(submenu: SubMenu): Long {
      return  dataSource.insert(submenu)
    }


    override suspend fun update(submenu: SubMenu) {
        dataSource.update(submenu)
    }

    override suspend fun update(items: MutableList<SubMenu>) {

    }

    override suspend fun delete(submenu: SubMenu) {
        dataSource.delete(submenu)
    }

    override fun getAllSubMenu(): LiveData<MutableList<SubMenu>> {
        return dataSource.getAllSubMenu()
    }

    override fun getAllSelectedMenus(): LiveData<MutableList<SubMenu>> {
       return dataSource.getAllSelectedMenus()
    }

    override suspend fun getSubMenuApi(azureId: String): Response<SubMenuResponse> {
        return remoteSubMenuDataSource.getSubMenuApi(azureId)
    }

}
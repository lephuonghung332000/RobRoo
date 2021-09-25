package net.mindzone.robroo.data.subMenu.remote

import net.mindzone.robroo.data.applicationSetting.entity.ApplicationResponse
import net.mindzone.robroo.data.subMenu.entity.SubMenu
import net.mindzone.robroo.data.subMenu.entity.SubMenuResponse
import retrofit2.Response

interface RemoteSubMenuDataSource {
    suspend fun getSubMenuApi(azureId : String) : Response<SubMenuResponse>
}
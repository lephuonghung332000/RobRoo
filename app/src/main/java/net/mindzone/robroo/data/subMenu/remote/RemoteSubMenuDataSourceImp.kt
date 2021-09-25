package net.mindzone.robroo.data.subMenu.remote

import net.mindzone.robroo.data.subMenu.entity.SubMenuResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteSubMenuDataSourceImp(val apiClient: ApiClient): RemoteSubMenuDataSource {

    override suspend fun getSubMenuApi(azureId: String): Response<SubMenuResponse> {
        return apiClient.getSubMenuApi(azureId)
    }

}
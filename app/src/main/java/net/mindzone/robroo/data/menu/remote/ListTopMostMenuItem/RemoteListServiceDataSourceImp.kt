package net.mindzone.robroo.data.menu.remote.ListTopMostMenuItem

import net.mindzone.robroo.data.menu.entity.ListTopMostMenuItem.ListServiceResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteListServiceDataSourceImp (val apiClient: ApiClient): RemoteListServiceDataSource {
    override suspend fun getListService(azureId: String,id:String):
            Response<ListServiceResponse> = apiClient.getListService(azureId,id)
}
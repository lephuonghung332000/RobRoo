package net.mindzone.robroo.data.menu.remote

import net.mindzone.robroo.data.menu.entity.listNewUnderTopMostLevel.ListNewsTopmostResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteListNewsTopMostDataSourceImp (val apiClient: ApiClient): RemoteListNewsTopMostDataSource {
    override suspend fun getListNewTopMost(azureId: String):
            Response<ListNewsTopmostResponse> = apiClient.getListNewsTopMost(azureId)


}
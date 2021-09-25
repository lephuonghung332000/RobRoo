package net.mindzone.robroo.data.productgroupList.remote

import net.mindzone.robroo.data.productgroupList.entity.SubGroupsResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteSubGroupsDataSourceImp (val apiClient: ApiClient): RemoteSubGroupDataSource {
    override suspend fun getSubGroups(
            azureId: String
    ): Response<SubGroupsResponse> = apiClient.getSubGroups(azureId)
}
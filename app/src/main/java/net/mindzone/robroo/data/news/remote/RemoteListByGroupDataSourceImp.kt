package net.mindzone.robroo.data.news.remote

import net.mindzone.robroo.data.news.entity.ListByGroupResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteListByGroupDataSourceImp (val apiClient: ApiClient): RemoteListByGroupDataSource {
    override suspend fun getListByGroup(azureId: String, group_id: Int):
            Response<ListByGroupResponse> = apiClient.getListByGroup(azureId,group_id)
}
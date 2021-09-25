package net.mindzone.robroo.data.news.repository

import net.mindzone.robroo.data.news.entity.ListByGroupResponse
import net.mindzone.robroo.data.news.remote.RemoteListByGroupDataSource
import retrofit2.Response

class ListByGroupRepositoryImp (val remoteListByGroupDataSource: RemoteListByGroupDataSource) : ListByGroupRepository {
    override suspend fun getListByGroup(azureId: String, group_id: Int):
            Response<ListByGroupResponse> = remoteListByGroupDataSource.getListByGroup(azureId,group_id)

}
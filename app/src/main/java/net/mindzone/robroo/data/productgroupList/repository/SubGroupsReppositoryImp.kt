package net.mindzone.robroo.data.productgroupList.repository

import net.mindzone.robroo.data.productgroupList.entity.SubGroupsResponse
import net.mindzone.robroo.data.productgroupList.remote.RemoteSubGroupDataSource
import retrofit2.Response

class SubGroupsReppositoryImp(val remoteSubGroupDataSource: RemoteSubGroupDataSource ): SubGroupsRepository{
    override suspend fun getSubGroups(
            azureId: String
    ): Response<SubGroupsResponse> = remoteSubGroupDataSource.getSubGroups(azureId)

}
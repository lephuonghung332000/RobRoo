package net.mindzone.robroo.data.productgroupList.remote

import net.mindzone.robroo.data.productgroupList.entity.SubGroupsResponse
import retrofit2.Response

interface RemoteSubGroupDataSource {
    suspend fun getSubGroups(azureId:String) : Response<SubGroupsResponse>
}
package net.mindzone.robroo.data.news.remote

import net.mindzone.robroo.data.news.entity.ListByGroupResponse
import retrofit2.Response

interface RemoteListByGroupDataSource {
    suspend fun getListByGroup(azureId:String,group_id:Int) : Response<ListByGroupResponse>
}
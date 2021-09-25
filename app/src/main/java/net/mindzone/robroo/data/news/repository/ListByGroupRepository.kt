package net.mindzone.robroo.data.news.repository

import net.mindzone.robroo.data.news.entity.ListByGroupResponse
import retrofit2.Response

interface ListByGroupRepository {
    suspend fun getListByGroup(
            azureId: String,
            group_id:Int
    ): Response<ListByGroupResponse>
}
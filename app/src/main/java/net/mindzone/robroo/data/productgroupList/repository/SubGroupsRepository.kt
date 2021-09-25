package net.mindzone.robroo.data.productgroupList.repository

import net.mindzone.robroo.data.productgroupList.entity.SubGroupsResponse
import retrofit2.Response

interface SubGroupsRepository {
    suspend fun getSubGroups(
            azureId: String,
    ): Response<SubGroupsResponse>
}
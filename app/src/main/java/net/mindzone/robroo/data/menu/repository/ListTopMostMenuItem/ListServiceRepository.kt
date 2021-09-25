package net.mindzone.robroo.data.menu.repository.ListTopMostMenuItem

import net.mindzone.robroo.data.menu.entity.ListTopMostMenuItem.ListServiceResponse
import retrofit2.Response

interface ListServiceRepository {
    suspend fun getListService(
            azureId: String,
            id:String
    ): Response<ListServiceResponse>
}
package net.mindzone.robroo.data.productmodelListimplement.repository

import net.mindzone.robroo.data.productmodelListimplement.entity.ListImplementResponse
import retrofit2.Response

interface ListImplementRepository {
    suspend fun getListImplement(
            azureId: String,
            model_id:String
    ): Response<ListImplementResponse>
}
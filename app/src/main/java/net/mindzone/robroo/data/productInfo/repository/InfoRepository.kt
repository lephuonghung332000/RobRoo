package net.mindzone.robroo.data.productInfo.repository

import net.mindzone.robroo.data.productGetWarranty.entity.WarrantyResponse
import net.mindzone.robroo.data.productInfo.entity.InfoResponse
import retrofit2.Response

interface InfoRepository {
    suspend fun getInfo(
        azureId: String,
        model_id:String
    ): Response<InfoResponse>
}
package net.mindzone.robroo.data.productGetWarranty.repository

import net.mindzone.robroo.data.productGetWarranty.entity.WarrantyResponse
import retrofit2.Response

interface WarrantyRepository {
    suspend fun getWarranty(
        azureId: String,
        model_id:String
    ): Response<WarrantyResponse>
}
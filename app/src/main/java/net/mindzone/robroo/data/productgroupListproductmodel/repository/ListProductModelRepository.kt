package net.mindzone.robroo.data.productgroupListproductmodel.repository

import net.mindzone.robroo.data.productgroupListproductmodel.entity.ListProductModelResponse
import retrofit2.Response

interface ListProductModelRepository {
    suspend fun getListProductModel(
            azureId: String,
            group_id:Int
    ): Response<ListProductModelResponse>
}
package net.mindzone.robroo.data.productModel.repository

import net.mindzone.robroo.data.productModel.entity.FieldsReponse
import retrofit2.Response

interface FieldsRepository {
    suspend fun getFields(
            azureId: String,
            model_id:String
    ): Response<FieldsReponse>
}
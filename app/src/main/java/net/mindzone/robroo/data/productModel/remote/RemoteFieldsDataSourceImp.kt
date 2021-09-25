package net.mindzone.robroo.data.productModel.remote

import net.mindzone.robroo.data.productModel.entity.FieldsReponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteFieldsDataSourceImp (val apiClient: ApiClient): RemoteFieldsDataSource {
    override suspend fun getFields(
            azureId: String,
            model_id:String
    ): Response<FieldsReponse> = apiClient.getField(azureId,model_id)


}
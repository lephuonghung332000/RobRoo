package net.mindzone.robroo.data.productGetWarranty.remote

import net.mindzone.robroo.data.productGetWarranty.entity.WarrantyResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteWarrantyDataSourceImp (val apiClient: ApiClient): RemoteWarrantyDataSource {
    override suspend fun getWarrantyInfo(
        azureId: String,
        model_id:String
    ): Response<WarrantyResponse> = apiClient.getWarrantyInfo( azureId, model_id)


}
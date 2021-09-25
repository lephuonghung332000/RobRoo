package net.mindzone.robroo.data.productgroupListproductmodel.remote

import net.mindzone.robroo.data.productgroupListproductmodel.entity.ListProductModelResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteListProductModelDataSourceImp (val apiClient: ApiClient): RemoteListProductModelDataSource {
    override suspend fun getListProductModel(azureId: String,group_id:Int):
            Response<ListProductModelResponse> = apiClient.getListProductModel(azureId,group_id)
}
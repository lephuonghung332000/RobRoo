package net.mindzone.robroo.data.productInfo.remote
import net.mindzone.robroo.data.productInfo.entity.InfoResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteInfoDataSourceImp (val apiClient: ApiClient): RemoteInfoDataSource {
    override suspend fun getInfo(
        azureId: String,
        model_id:String
    ): Response<InfoResponse> = apiClient.getInfo( azureId, model_id)


}
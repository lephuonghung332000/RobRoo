package net.mindzone.robroo.data.productmodelListimplement.remote
import net.mindzone.robroo.data.productmodelListimplement.entity.ListImplementResponse
import net.mindzone.robroo.data.productmodelListimplement.repository.ListImplementRepository
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response
class RemoteListImplementDataSourceImp (val apiClient: ApiClient): RemoteListImplementDataSource {
    override suspend fun getListImplement(azureId: String,model_id:String):
            Response<ListImplementResponse> = apiClient.getListImplement(azureId,model_id)

}
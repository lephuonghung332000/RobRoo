package net.mindzone.robroo.data.productmodelListimplement.repository

import net.mindzone.robroo.data.productmodelListimplement.entity.ListImplementResponse
import net.mindzone.robroo.data.productmodelListimplement.remote.RemoteListImplementDataSource
import retrofit2.Response

class ListImplementRepositoryImp (val remoteListImplementDataSource: RemoteListImplementDataSource) : ListImplementRepository {
    override suspend fun getListImplement(azureId: String,model_id:String):
            Response<ListImplementResponse> = remoteListImplementDataSource.getListImplement(azureId,model_id)

}
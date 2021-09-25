package net.mindzone.robroo.data.productgroupListproductmodel.repository

import net.mindzone.robroo.data.productgroupListproductmodel.entity.ListProductModelResponse
import net.mindzone.robroo.data.productgroupListproductmodel.remote.RemoteListProductModelDataSource
import retrofit2.Response

class ListProductModelRepositoryImp (val remoteListProductModelDataSource: RemoteListProductModelDataSource) : ListProductModelRepository {
    override suspend fun getListProductModel(azureId: String,group_id:Int):
            Response<ListProductModelResponse> = remoteListProductModelDataSource.getListProductModel(azureId,group_id)
}
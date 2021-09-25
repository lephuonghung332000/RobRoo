package net.mindzone.robroo.data.productgroupListproductmodel.remote

import net.mindzone.robroo.data.productgroupListproductmodel.entity.ListProductModelResponse
import retrofit2.Response

interface RemoteListProductModelDataSource {
    suspend fun getListProductModel(azureId:String,group_id:Int) : Response<ListProductModelResponse>
}
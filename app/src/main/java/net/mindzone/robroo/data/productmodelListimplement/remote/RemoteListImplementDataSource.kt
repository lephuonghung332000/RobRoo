package net.mindzone.robroo.data.productmodelListimplement.remote

import net.mindzone.robroo.data.productmodelListimplement.entity.ListImplementResponse
import retrofit2.Response


interface RemoteListImplementDataSource {
    suspend fun getListImplement(azureId:String,model_id:String) : Response<ListImplementResponse>
}
package net.mindzone.robroo.data.productInfo.remote

import net.mindzone.robroo.data.productInfo.entity.InfoResponse
import retrofit2.Response

interface RemoteInfoDataSource {
    suspend fun getInfo(azureId:String,model_id:String) : Response<InfoResponse>
}
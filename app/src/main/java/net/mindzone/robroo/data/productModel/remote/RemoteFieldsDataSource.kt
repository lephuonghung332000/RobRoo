package net.mindzone.robroo.data.productModel.remote

import net.mindzone.robroo.data.productModel.entity.FieldsReponse
import retrofit2.Response

interface RemoteFieldsDataSource {
    suspend fun getFields(azureId:String,model_id:String) : Response<FieldsReponse>
}
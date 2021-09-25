package net.mindzone.robroo.data.productModel.repository

import net.mindzone.robroo.data.productModel.entity.FieldsReponse
import net.mindzone.robroo.data.productModel.remote.RemoteFieldsDataSource
import retrofit2.Response
class FieldsRepositoryImp (val remoteFieldsDataSource: RemoteFieldsDataSource) : FieldsRepository {
    override suspend fun getFields(
            azureId: String,
            model_id:String
    ): Response<FieldsReponse> = remoteFieldsDataSource.getFields(azureId,model_id)
}
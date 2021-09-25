package net.mindzone.robroo.data.productGetWarranty.repository

import net.mindzone.robroo.data.productGetWarranty.entity.WarrantyResponse
import net.mindzone.robroo.data.productGetWarranty.remote.RemoteWarrantyDataSource
import retrofit2.Response

class WarrantyRepositoryImp (val remoteWarrantyDataSource: RemoteWarrantyDataSource) :
    WarrantyRepository {
    override suspend fun getWarranty(
        azureId: String,
        model_id:String
    ): Response<WarrantyResponse> = remoteWarrantyDataSource.getWarrantyInfo(azureId,model_id)
}
package net.mindzone.robroo.data.productInfo.repository

import net.mindzone.robroo.data.productGetWarranty.entity.WarrantyResponse
import net.mindzone.robroo.data.productGetWarranty.remote.RemoteWarrantyDataSource
import net.mindzone.robroo.data.productGetWarranty.repository.WarrantyRepository
import net.mindzone.robroo.data.productInfo.entity.InfoResponse
import net.mindzone.robroo.data.productInfo.remote.RemoteInfoDataSource
import retrofit2.Response

class InfoRepositoryImp (val remoteInfoDataSource: RemoteInfoDataSource) :
    InfoRepository {
    override suspend fun getInfo(
        azureId: String,
        model_id:String
    ): Response<InfoResponse> = remoteInfoDataSource.getInfo(azureId,model_id)
}
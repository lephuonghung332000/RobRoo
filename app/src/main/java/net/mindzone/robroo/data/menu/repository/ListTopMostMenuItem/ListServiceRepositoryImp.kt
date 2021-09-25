package net.mindzone.robroo.data.menu.repository.ListTopMostMenuItem

import net.mindzone.robroo.data.menu.entity.ListTopMostMenuItem.ListServiceResponse
import net.mindzone.robroo.data.menu.remote.ListTopMostMenuItem.RemoteListServiceDataSource
import retrofit2.Response

class ListServiceRepositoryImp(val remoteListServiceDataSource: RemoteListServiceDataSource): ListServiceRepository {
    override suspend fun getListService(azureId: String,id:String):
            Response<ListServiceResponse> = remoteListServiceDataSource.getListService(azureId,id)

}
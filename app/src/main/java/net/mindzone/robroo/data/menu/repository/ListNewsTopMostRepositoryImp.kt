package net.mindzone.robroo.data.menu.repository

import net.mindzone.robroo.data.menu.entity.listNewUnderTopMostLevel.ListNewsTopmostResponse
import net.mindzone.robroo.data.menu.remote.RemoteListNewsTopMostDataSource
import retrofit2.Response

class ListNewsTopMostRepositoryImp(val remoteListNewsTopMostDataSource: RemoteListNewsTopMostDataSource): ListNewsTopMostRepository {
    override suspend fun getListNewsTopMost(azureId: String):
            Response<ListNewsTopmostResponse> = remoteListNewsTopMostDataSource.getListNewTopMost(azureId)

}
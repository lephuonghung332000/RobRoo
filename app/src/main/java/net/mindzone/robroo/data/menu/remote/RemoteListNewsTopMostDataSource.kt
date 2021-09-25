package net.mindzone.robroo.data.menu.remote

import net.mindzone.robroo.data.menu.entity.listNewUnderTopMostLevel.ListNewsTopmostResponse
import retrofit2.Response

interface RemoteListNewsTopMostDataSource {
    suspend fun getListNewTopMost(azureId:String) : Response<ListNewsTopmostResponse>
}
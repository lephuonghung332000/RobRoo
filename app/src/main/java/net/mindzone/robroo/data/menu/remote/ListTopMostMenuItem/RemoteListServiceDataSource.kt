package net.mindzone.robroo.data.menu.remote.ListTopMostMenuItem

import net.mindzone.robroo.data.menu.entity.ListTopMostMenuItem.ListServiceResponse
import retrofit2.Response

interface RemoteListServiceDataSource {
    suspend fun getListService(azureId:String,id:String) : Response<ListServiceResponse>
}
package net.mindzone.robroo.data.user.remote

import net.mindzone.robroo.data.utils.ApiClient

class RemoteUserDataSourceImp(private val apiClient: ApiClient) : RemoteUserDataSource {
    override suspend fun login(azureId:String) =
        apiClient.login(azureId)
}
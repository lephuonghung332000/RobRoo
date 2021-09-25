package net.mindzone.robroo.data.token.remote

import net.mindzone.robroo.data.token.entity.TokenResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteTokenDataSourceImp(val apiClient: ApiClient) : RemoteTokenDataSource{
    override suspend fun setPushToken(
        token: String,
        azure_id: String,
        os: String,
        enabled: String
    ): Response<TokenResponse> = apiClient.setPushToken(token,azure_id,os, enabled)
}
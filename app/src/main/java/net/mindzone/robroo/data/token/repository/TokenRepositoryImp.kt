package net.mindzone.robroo.data.token.repository

import net.mindzone.robroo.data.token.entity.TokenResponse
import net.mindzone.robroo.data.token.remote.RemoteTokenDataSource
import retrofit2.Response

class TokenRepositoryImp(val tokenDataSource: RemoteTokenDataSource) : TokenRepository {
    override  suspend fun setPushToken(
        token: String,
        azure_id: String,
        os: String,
        enabled: String
    ): Response<TokenResponse> = tokenDataSource.setPushToken(token,azure_id,os, enabled)
}
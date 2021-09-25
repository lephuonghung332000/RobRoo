package net.mindzone.robroo.data.token.remote
import net.mindzone.robroo.data.token.entity.TokenResponse
import retrofit2.Response

interface RemoteTokenDataSource {
     suspend fun setPushToken(token: String, azure_id : String, os : String, enabled: String) : Response<TokenResponse>
}
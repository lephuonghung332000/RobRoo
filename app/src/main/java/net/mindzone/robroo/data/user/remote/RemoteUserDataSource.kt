package net.mindzone.robroo.data.user.remote

import net.mindzone.robroo.data.user.entity.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface RemoteUserDataSource {
    suspend fun login(azureId:String) : Response<LoginResponse>
}
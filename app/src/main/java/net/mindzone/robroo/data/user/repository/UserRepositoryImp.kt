package net.mindzone.robroo.data.user.repository

import net.mindzone.robroo.data.user.entity.LoginResponse
import net.mindzone.robroo.data.user.remote.RemoteUserDataSource
import okhttp3.ResponseBody
import retrofit2.Response

class UserRepositoryImp internal constructor(private val dataSource: RemoteUserDataSource) :
    UserRepository {
    override suspend fun login(azureId: String): Response<LoginResponse> = dataSource.login(azureId)
}
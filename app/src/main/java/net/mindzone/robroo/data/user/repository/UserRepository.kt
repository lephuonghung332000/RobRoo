package net.mindzone.robroo.data.user.repository

import net.mindzone.robroo.data.user.entity.LoginResponse
import retrofit2.Response

interface UserRepository {
    suspend fun login(azureId:String): Response<LoginResponse>
}
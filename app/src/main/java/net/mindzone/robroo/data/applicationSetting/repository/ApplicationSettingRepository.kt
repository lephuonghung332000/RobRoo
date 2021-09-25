package net.mindzone.robroo.data.applicationSetting.repository

import net.mindzone.robroo.data.applicationSetting.entity.ApplicationResponse
import retrofit2.Response

interface ApplicationSettingRepository {
    suspend fun getApplicationSetting() : Response<ApplicationResponse>
}
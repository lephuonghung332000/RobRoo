package net.mindzone.robroo.data.applicationSetting.remote

import net.mindzone.robroo.data.applicationSetting.entity.ApplicationResponse
import net.mindzone.robroo.data.notification.entity.NotificationResponse
import retrofit2.Response

interface RemoteAppliationSettingDataSource {
    suspend fun getApplicationSetting() : Response<ApplicationResponse>
}
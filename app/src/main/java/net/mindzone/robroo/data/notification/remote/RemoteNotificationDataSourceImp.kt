package net.mindzone.robroo.data.notification.remote

import net.mindzone.robroo.data.notification.entity.Notification
import net.mindzone.robroo.data.notification.entity.NotificationResponse
import net.mindzone.robroo.data.user.entity.LoginResponse
import net.mindzone.robroo.data.user.remote.RemoteUserDataSource
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteNotificationDataSourceImp (val apiClient: ApiClient): RemoteNotificationDataSource {
    override suspend fun getNotification(
        type: String,
        azureId: String,
        page: Int
    ): Response<NotificationResponse> = apiClient.getNotifications(type, azureId, page)


}
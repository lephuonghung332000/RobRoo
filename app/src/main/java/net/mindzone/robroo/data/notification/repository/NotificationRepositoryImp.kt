package net.mindzone.robroo.data.notification.repository

import net.mindzone.robroo.data.notification.entity.NotificationResponse
import net.mindzone.robroo.data.notification.remote.RemoteNotificationDataSource
import retrofit2.Response

class NotificationRepositoryImp (val remoteNotificationDataSource: RemoteNotificationDataSource) : NotificationRepository {
    override suspend fun getNotification(
        type: String,
        azureId: String,
        page: Int
    ): Response<NotificationResponse> = remoteNotificationDataSource.getNotification(type, azureId, page)
}
package net.mindzone.robroo.data.notification.repository

import net.mindzone.robroo.data.notification.entity.NotificationResponse
import retrofit2.Response

interface NotificationRepository {
    suspend fun getNotification(
        type: String,
        azureId: String,
        page: Int
    ): Response<NotificationResponse>
}
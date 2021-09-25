package net.mindzone.robroo.data.notification.remote

import net.mindzone.robroo.data.notification.entity.Notification
import net.mindzone.robroo.data.notification.entity.NotificationResponse
import net.mindzone.robroo.data.user.entity.LoginResponse
import retrofit2.Response

interface RemoteNotificationDataSource {
    suspend fun getNotification(type: String,azureId:String,page : Int ) : Response<NotificationResponse>
}
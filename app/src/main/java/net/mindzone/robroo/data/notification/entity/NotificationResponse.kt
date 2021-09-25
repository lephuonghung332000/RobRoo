package net.mindzone.robroo.data.notification.entity

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCode

data class NotificationResponse(
    val responseCode: ResponseCode,
    val responseData: ResponseData
)


data class ResponseData(
    @SerializedName("total")
    val total: String = "",
    @SerializedName("notifications")
    val notifications: List<Notification>
)

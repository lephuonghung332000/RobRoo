package net.mindzone.robroo.data.notification.entity

import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("notification_id")
    val notification_id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("body")
    val body: String = "",
    @SerializedName("lastname")
    val date: String = "" ,
    @SerializedName("type")
    val type: String = "",
    @SerializedName("read")
    val read: String = "",
    @SerializedName("foreign_id")
    val foreign_id: Int = 0
)
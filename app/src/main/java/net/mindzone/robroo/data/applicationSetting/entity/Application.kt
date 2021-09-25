package net.mindzone.robroo.data.applicationSetting.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Application (
    @SerializedName("offline")
    val offline: String = "",
    @SerializedName("offlineMessage")
    val offlineMessage: String = "",
    @SerializedName("contactPhone")
    val contactPhone: String = "",
    @SerializedName("contactEmail")
    val contactEmail: String = ""
): Serializable
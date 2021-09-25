package net.mindzone.robroo.data.applicationSetting.entity

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCode

class ApplicationResponse(
    val responseCode: ResponseCode,
    val responseData: ResponseData
)

data class ResponseData(
    @SerializedName("application")
    val application: Application
)
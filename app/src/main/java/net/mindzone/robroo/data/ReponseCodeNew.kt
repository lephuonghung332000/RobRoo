package net.mindzone.robroo.data

import com.google.gson.annotations.SerializedName

data class ResponseCodeNew(
        @SerializedName("errorCode")
        val errorCode: Int,
        @SerializedName("errorMessage")
        val errorMessage: String,
        @SerializedName("curlInfo")
        val curlInfo: String,
        @SerializedName("elapsedTime")
        val elapsedTime: Double,
        @SerializedName("elapsedQueries")
        val elapsedQueries: List<ElapsedQueries>
)


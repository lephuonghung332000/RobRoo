package net.mindzone.robroo.data

import com.google.gson.annotations.SerializedName

data class ResponseCode(
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMessage")
    val errorMessage: String,
    @SerializedName("elapsedTime")
    val elapsedTime: Double,
    @SerializedName("elapsedQueries")
    val elapsedQueries: List<ElapsedQueries>
)

data class ElapsedQueries(
    @SerializedName("elapsedQuery")
    val elapsedQuery: String,
    @SerializedName("elapsedTime")
    val elapsedTime: Double,

)

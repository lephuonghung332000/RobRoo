package net.mindzone.robroo.data.manualDocument.entity.documentSearch

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCodeNew
import net.mindzone.robroo.data.manualDocument.entity.listDocument.Items
import java.io.Serializable
data class DocumentSearchResponse(
    val responseCodeNew: ResponseCodeNew,
    val responseData: ResponseData
)
data class ResponseData(
    @SerializedName("hasmore")
    val hasmore: Int = 0,
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("items")
    val items: List<Items>,
    ): Serializable
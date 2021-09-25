package net.mindzone.robroo.data.menu.entity.listNewUnderTopMostLevel

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCodeNew

data class ListNewsTopmostResponse(
        val responseCodeNew: ResponseCodeNew,
        val responseData: List<ResponseData>
)

data class ResponseData(
        @SerializedName("groupid")
        val groupid: Int = 0,
        @SerializedName("code")
        val code: String = "",
        @SerializedName("titleth")
        val titleth: String = "",
        @SerializedName("titleen")
        val titleen: String = "",
        @SerializedName("layout")
        val layout: String = "",
        @SerializedName("items")
        val items: List<Items>
)
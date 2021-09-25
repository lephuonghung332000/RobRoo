package net.mindzone.robroo.data.news.entity

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCodeNew
import net.mindzone.robroo.data.menu.entity.listNewUnderTopMostLevel.Items

data class ListByGroupResponse(
        val responseCodeNew: ResponseCodeNew,
        val responseData: ResponseData
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
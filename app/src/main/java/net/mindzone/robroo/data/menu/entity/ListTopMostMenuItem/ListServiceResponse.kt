package net.mindzone.robroo.data.menu.entity.ListTopMostMenuItem

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCodeNew

data class ListServiceResponse(
        val responseCodeNew: ResponseCodeNew,
        val responseData: List<ResponseData>
)

data class ResponseData(
        @SerializedName("itemid")
        val itemid: Int = 0,
        @SerializedName("parentid")
        val parentid: Int = 0,
        @SerializedName("code")
        val code: String = "",
        @SerializedName("titleth")
        val titleth: String = "",
        @SerializedName("titleen")
        val titleen: String = "",
        @SerializedName("shortdescth")
        val shortdescth: String = "",
        @SerializedName("shortdescen")
        val shortdescen: String = "",
        @SerializedName("action")
        val action: String = "",
        @SerializedName("actionlinkth")
        val actionlinkth: String = "",
        @SerializedName("actionlinken")
        val actionlinken: String = "",
        @SerializedName("seqno")
        val seqno: Int = 0,
        @SerializedName("visibility")
        val visibility: String = "",
        @SerializedName("icontype")
        val icontype: String = "",
        @SerializedName("iconimage1")
        val iconimageth: String = "",
        @SerializedName("iconimage2")
        val iconimageen: String = ""
)
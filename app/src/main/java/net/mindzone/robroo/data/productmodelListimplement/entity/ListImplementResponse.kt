package net.mindzone.robroo.data.productmodelListimplement.entity

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCodeNew

data class ListImplementResponse(
        val responseCodeNew: ResponseCodeNew,
        val responseData: List<ResponseData>
)
data class ResponseData(
        @SerializedName("modelid")
        val modelid: Int = 0,
        @SerializedName("modelcode")
        val modelcode: String = "",
        @SerializedName("modelprefix")
        val modelprefix: String = "",
        @SerializedName("groupid")
        val groupid: Int = 0,
        @SerializedName("grouptype")
        val grouptype: String = ""
)
package net.mindzone.robroo.data.manualDocument.entity.listDocumentType

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCodeNew
import java.io.Serializable

data class ListDocumentTypeResponse(
        val responseCodeNew: ResponseCodeNew,
        val responseData: List<ResponseData>
)
data class ResponseData(
        @SerializedName("typeid")
        val typeid: Int = 0,
        @SerializedName("abbrv")
        val abbrv: String = "",
        @SerializedName("code")
        val code: String = "",
        @SerializedName("typenameth")
        val typenameth: String = "",
        @SerializedName("typenameen")
        val typenameen: String = ""
): Serializable{
        override fun toString(): String = typenameth
}
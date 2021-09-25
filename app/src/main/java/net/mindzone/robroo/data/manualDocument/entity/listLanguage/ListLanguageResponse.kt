package net.mindzone.robroo.data.manualDocument.entity.listLanguage

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCodeNew
import java.io.Serializable

data class ListLanguageResponse(
        val responseCodeNew: ResponseCodeNew,
        val responseData: List<ResponseData>
)
data class ResponseData(
        @SerializedName("langid")
        val langid: Int = 0,
        @SerializedName("abbrv")
        val abbrv: String = "",
        @SerializedName("langnameth")
        val langnameth: String = "",
        @SerializedName("langnameen")
        val langnameen: String = ""
): Serializable{
        override fun toString(): String = langnameth
}
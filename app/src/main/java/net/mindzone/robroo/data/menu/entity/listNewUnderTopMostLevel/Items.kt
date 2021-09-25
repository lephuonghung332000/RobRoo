package net.mindzone.robroo.data.menu.entity.listNewUnderTopMostLevel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Items (
        @SerializedName("contentid")
        val contentid: Int = 0,
        @SerializedName("code")
        val code: String = "",
        @SerializedName("titleth")
        val titleth: String = "" ,
        @SerializedName("titleen")
        val titleen: String = "",
        @SerializedName("introth")
        val introth: String = "",
        @SerializedName("introen")
        val introen: String = "",
        @SerializedName("imageth")
        val imageth: String = "",
        @SerializedName("imageen")
        val imageen: String = "",
        @SerializedName("format")
        val format: String = "",
        @SerializedName("linkth")
        val linkth: String = "",
        @SerializedName("linken")
        val linken: String = "",
        @SerializedName("viewstats")
        val viewstats: Int = 0,
        @SerializedName("contentdatetime")
        val contentdatetime: String = "",
        @SerializedName("readstatus")
        val readstatus: Int = 0,
        @SerializedName("tags")
        val tags: List<String>
) : Serializable

class Tag()
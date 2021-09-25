package net.mindzone.robroo.data.productgroupList.entity

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCodeNew
import java.io.Serializable


data class SubGroupsResponse(
        val responseCodeNew: ResponseCodeNew,
        val responseData: List<ResponseData>
)

data class ResponseData(
        @SerializedName("groupid")
        val groupid: Int = 0,
        @SerializedName("groupnameth")
        val groupnameth: String = "",
        @SerializedName("groupnameen")
        val groupnameen: String = "",
        @SerializedName("grouptype")
        val grouptype: String = "",
        @SerializedName("subgroups")
        val subgroups: List<SubGroups>
):Serializable
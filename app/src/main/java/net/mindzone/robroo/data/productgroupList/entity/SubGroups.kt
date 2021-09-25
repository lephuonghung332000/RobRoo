package net.mindzone.robroo.data.productgroupList.entity

import com.google.gson.annotations.SerializedName

data class SubGroups (
    @SerializedName("groupid")
    val groupid: Int = 0,
    @SerializedName("groupnameth")
    val groupnameth: String = "",
    @SerializedName("groupnameen")
    val groupnameen: String = "" ,
    @SerializedName("grouptype")
    val grouptype: String = ""
)
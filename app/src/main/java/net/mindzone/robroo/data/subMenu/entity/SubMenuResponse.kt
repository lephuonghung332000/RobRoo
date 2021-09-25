package net.mindzone.robroo.data.subMenu.entity

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCode
import net.mindzone.robroo.data.applicationSetting.entity.Application

class SubMenuResponse(
    val responseCode: ResponseCode,
    val responseData: ResponseData
)

data class ResponseData(
    @SerializedName("widgets")
    val subMenu: MutableList<SubMenu>
)
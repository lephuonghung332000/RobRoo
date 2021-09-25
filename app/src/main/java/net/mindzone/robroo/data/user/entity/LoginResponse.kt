package net.mindzone.robroo.data.user.entity

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCode
import java.io.Serializable

data class LoginResponse(
    val responseCode: ResponseCode,
    val responseData: ResponseData
)

data class ResponseData(

    @SerializedName("staffid")
    val staffid: String,
    @SerializedName("firstnameth")
    val firstnameth: String,
    @SerializedName("lastnameth")
    val lastnameth: String,
    @SerializedName("firstnameen")
    val firstnameen: String,
    @SerializedName("lastnameen")
    val lastnameen: String,
    @SerializedName("positionth")
    var positionth: String,
    @SerializedName("positionen")
    var positionen: String,
    @SerializedName("positions")
    var positions: List<Positions>,
    @SerializedName("positiongroups")
    var positiongroups: List<PositionGroups> ,
    @SerializedName("orgtype")
    var orgtype: String ="",
    @SerializedName("orgcode")
    var orgcode: String ,
    @SerializedName("orgcountry")
    var orgcountry: String,
    @SerializedName("orgnameth")
    var orgnameth: String,
    @SerializedName("orgnameen")
    var orgnameen: String,
    @SerializedName("azureoid")
    var azureoid: String

) : Serializable
class PositionGroups(
    @SerializedName("groupcode")
    var groupcode: String ="" ,
    @SerializedName("groupnameth")
    var groupnameth: String="" ,
    @SerializedName("groupnameen")
    var groupnameen: String=""):Serializable

data class Positions(
    @SerializedName("positioncode")
    var positioncode: String ="",
    @SerializedName("positionnameth")
    var positionnameth: String ="",
    @SerializedName("positionnameen")
    var positionnameen: String =""
):Serializable
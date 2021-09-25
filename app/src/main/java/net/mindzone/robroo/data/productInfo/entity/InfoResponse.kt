package net.mindzone.robroo.data.productInfo.entity

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCode

data class InfoResponse(
    val responseCode: ResponseCode,
    val responseData: ResponseData
)
data class ResponseData(
    @SerializedName("modelid")
    val model_id: Int = 0,
    @SerializedName("modelcode")
    val model_code: String?= "",
    @SerializedName("groupid")
    val group_id: Int = 0,
    @SerializedName("grouptype")
    val group_type: String = "",
    @SerializedName("groupnameth")
    val group_nameth: String = "",
    @SerializedName("groupnameen")
    val groupname_en: String = "",
    @SerializedName("productid")
    val produc_tid: Int = 0,
    @SerializedName("productnameth")
    val product_nameth: String = "",
    @SerializedName("productnameen")
    val productname_en: String = "",
    @SerializedName("modelprefix")
    val model_prefix: String = "",
    @SerializedName("materialno")
    val materia_lno: String = "" ,
    @SerializedName("modelimage")
    val model_image: String = "",
    @SerializedName("horsepower")
    var horsepower: String? = "",
    @SerializedName("price")
    val price: Int = 0,
    @SerializedName("warrantyhours")
    val warranty_hours: String = "",
    @SerializedName("warrantymonths")
    val warranty_months: String = "",
    @SerializedName("warrantyyears")
    val warranty_years: String = "",
    @SerializedName("warrantydetail")
    val warranty_detail: String = ""


)
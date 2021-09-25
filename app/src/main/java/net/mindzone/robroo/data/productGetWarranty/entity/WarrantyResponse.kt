package net.mindzone.robroo.data.productGetWarranty.entity
import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCode

data class WarrantyResponse(
    val responseCode: ResponseCode,
    val responseData: ResponseData
)
data class ResponseData(
    @SerializedName("modelid")
    val model_id: Int = 0,
    @SerializedName("modelcode")
    val model_code: String = "",
    @SerializedName("modelprefix")
    val model_prefix: String = "",
    @SerializedName("groupid")
    val group_id: Int = 0 ,
    @SerializedName("grouptype")
    val group_type: String = "",
    @SerializedName("warrantyhours")
    val warranty_hours: Int = 0,
    @SerializedName("warrantymonths")
    val warranty_months: Int = 0,
    @SerializedName("warrantyyears")
    val warranty_years: Int = 0,
    @SerializedName("warrantyperiod")
    val warranty_period: String="",
    @SerializedName("warrantydetail")
    val warranty_detail: String="",
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



)


package net.mindzone.robroo.data.productModel.entity.getSpecification
import com.google.gson.annotations.SerializedName
data class Fields (
    @SerializedName("fieldid")
    val fieldid: Int = 0,
    @SerializedName("fieldtype")
    val fieldtype: String = "",
    @SerializedName("seqno")
    val seqno: Int = 0,
    @SerializedName("fieldlabel")
    val fieldlabel: String? = "" ,
    @SerializedName("fieldunit")
    val fieldunit: String = "",
    @SerializedName("fieldvalue")
    val fieldvalue: String? = "",
    @SerializedName("reflink")
    val reflink: String = ""
)
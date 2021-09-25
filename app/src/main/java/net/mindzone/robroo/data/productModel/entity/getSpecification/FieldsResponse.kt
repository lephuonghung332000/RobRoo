package net.mindzone.robroo.data.productModel.entity

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCodeNew
import net.mindzone.robroo.data.productModel.entity.getSpecification.Fields

data class FieldsReponse(
        val responseCodeNew: ResponseCodeNew,
        val responseData: List<ResponseData>
)

data class ResponseData(
        @SerializedName("formid")
        val formid: Int = 0,
        @SerializedName("formtitle")
        val formtitle: String = "",
        @SerializedName("fields")
        val fields: ArrayList<Fields>
)

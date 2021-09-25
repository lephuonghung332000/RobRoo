package net.mindzone.robroo.data.faqList.entity

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCode


class FaqListResponse (
    val responseCode: ResponseCode,
    val responseData: ResponseData
)

data class ResponseData(
    @SerializedName("faq")
    val faq: List<FaqList>

)
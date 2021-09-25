package net.mindzone.robroo.data.faqArticle.entity
import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCode


class FaqArticleResponse (
    val responseCode: ResponseCode,
    val responseData: ResponseData
)

data class ResponseData(
    @SerializedName("faqarticle")
    val faqarticle: FaqArticle

)
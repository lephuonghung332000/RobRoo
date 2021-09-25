package net.mindzone.robroo.data.forumArticle.entity.forumArticles

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCode

data class ForumArticlesResponse(
    val responseCode: ResponseCode,
    val responseData: ResponseData
)

data class ResponseData(
    @SerializedName("total")
    val total: Int,
    @SerializedName("forumarticles")
    val forumArticles: List<ForumArticles>
)
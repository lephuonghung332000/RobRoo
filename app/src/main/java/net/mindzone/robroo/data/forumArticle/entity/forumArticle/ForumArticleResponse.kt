package net.mindzone.robroo.data.forumArticle.entity.forumArticle

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCode

data class ForumArticleResponse(
    val responseCode: ResponseCode,
    val responseData: ResponseDataForumArticle
)
data class ResponseDataForumArticle(
    @SerializedName("forumarticle")
    val forumArticle: ForumArticle
)

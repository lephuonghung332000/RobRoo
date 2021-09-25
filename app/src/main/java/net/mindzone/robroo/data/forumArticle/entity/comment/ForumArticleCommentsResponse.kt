package net.mindzone.robroo.data.forumArticle.entity.comment

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCode

data class ForumArticleCommentsResponse(
    val responseCode: ResponseCode,
    @SerializedName("responseData")
    val responseData: ResponseDataComments
)

data class ResponseDataComments(
    @SerializedName("comments")
    val comments: MutableList<Comment>
)

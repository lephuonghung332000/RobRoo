package net.mindzone.robroo.data.forumArticle.entity.createForumArticle

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LikeForumArticleCommentRequest(
    @SerializedName("article_id")
    val articleId: String = "",

    @SerializedName("azure_id")
    val azureId: String = "",

    @SerializedName("like")
    val parentId: Int = 0,

) : Serializable
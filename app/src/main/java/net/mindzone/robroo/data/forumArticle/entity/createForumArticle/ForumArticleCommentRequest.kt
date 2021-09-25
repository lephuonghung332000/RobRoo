package net.mindzone.robroo.data.forumArticle.entity.createForumArticle

import com.google.gson.annotations.SerializedName

data class ForumArticleCommentRequest(
    @SerializedName("article_id")
    val articleId:String = "",

    @SerializedName("azure_id")
    val azureId:String = "",

    @SerializedName("parent_id")
    val parentId:String = "",

    @SerializedName("text")
    val text:String = "",
)

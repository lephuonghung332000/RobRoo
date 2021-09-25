package net.mindzone.robroo.data.forumArticle.entity.forumArticle

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForumArticle(
    @SerializedName("article_id")
    val articleId: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("desc")
    val desc: String = "",
    @SerializedName("author")
    val author: String = "",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("azure_id")
    val azureId: String = "",
    @SerializedName("isLiked")
    val isLiked: Int = -1,
    @SerializedName("likes")
    val likes: Int = -1,
    @SerializedName("read")
    val read: Int = -1,
    @SerializedName("commentcount")
    val commentCount: Int = -1,
    @SerializedName("viewcount")
    val viewCount: Int = -1,
    @SerializedName("tags")
    val tags: List<String> = emptyList(),
    @SerializedName("images")
    val images: List<String> = emptyList()
) : Serializable

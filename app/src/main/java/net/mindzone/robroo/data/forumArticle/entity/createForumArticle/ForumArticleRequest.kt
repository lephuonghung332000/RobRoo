package net.mindzone.robroo.data.forumArticle.entity.createForumArticle

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ForumArticleRequest(
    @SerializedName("article_id")
    val articleId: String = "",
    @SerializedName("azure_id")
    val azureId: String = "",
    @SerializedName("tags")
    val tags: List<String> = emptyList(),
    @SerializedName("images")
    val images: List<String> = emptyList(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("text")
    val text: String = "",
    @SerializedName("group_id")
    val groupId: String = "",
) : Serializable
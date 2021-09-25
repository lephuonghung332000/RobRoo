package net.mindzone.robroo.data.forumArticle.entity.comment

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Comment(
    @SerializedName("comment_id")
    val commentId: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("azure_id")
    val azureId: String,
    @SerializedName("isLiked")
    val isLiked: Int,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("replies")
    val replies: List<Reply>,
) : Serializable

data class Reply(
    @SerializedName("comment_id")
    val commentId: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("azure_id")
    val azureId: String,
) : Serializable
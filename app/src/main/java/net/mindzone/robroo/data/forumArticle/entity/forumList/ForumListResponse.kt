package net.mindzone.robroo.data.forumArticle.entity.forumList

import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.data.ResponseCode

data class ForumListResponse(
    val responseCode: ResponseCode,
    @SerializedName("responseData")
    val responseData: ResponseDataForumList
)

data class ResponseDataForumList(
    @SerializedName("forumgroups")
    val forumGroups: List<ForumGroups>
)

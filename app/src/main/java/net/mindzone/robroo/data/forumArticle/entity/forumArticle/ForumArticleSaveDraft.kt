package net.mindzone.robroo.data.forumArticle.entity.forumArticle

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "forumArticles", indices = [
        Index(value = ["forumArticleId"])
    ]
)

data class ForumArticleSaveDraft(
    @PrimaryKey(autoGenerate = true)
    var forumArticleId: Long = 0,
    val groupId: String = "",
    var title: String = "",
    var text: String = "",
    var author: String = "",
    var date: String = "",
    var azureId: String = "",
    var read: Int = 0,
    var commentCount: Int = 0,
    var viewCount: Int = 0,
    var tags: List<String> = emptyList(),
    var images: List<String> = emptyList()
):Serializable
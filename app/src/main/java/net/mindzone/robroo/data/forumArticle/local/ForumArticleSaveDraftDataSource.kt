package net.mindzone.robroo.data.forumArticle.local

import androidx.lifecycle.LiveData
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleSaveDraft

interface ForumArticleSaveDraftDataSource {
    suspend fun insertForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft): Long
    fun getAllForumArticlesSaveDraft(groupId:String): LiveData<List<ForumArticleSaveDraft>>
    suspend fun deleteForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft)
    suspend fun updateForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft)
}
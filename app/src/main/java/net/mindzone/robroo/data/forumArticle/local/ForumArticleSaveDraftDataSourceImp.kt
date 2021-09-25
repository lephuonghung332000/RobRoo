package net.mindzone.robroo.data.forumArticle.local

import androidx.lifecycle.LiveData
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleSaveDraft

class ForumArticleSaveDraftDataSourceImp internal constructor(private val forumArticleSaveDraftDao: ForumArticleSaveDraftDao) :
    ForumArticleSaveDraftDataSource {
    override suspend fun insertForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft) =
        forumArticleSaveDraftDao.insertForumArticleSaveDraft(forumArticleSaveDraft)

    override fun getAllForumArticlesSaveDraft(groupId:String): LiveData<List<ForumArticleSaveDraft>> =
        forumArticleSaveDraftDao.getAllForumArticlesSaveDraft(groupId)

    override suspend fun deleteForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft) =
        forumArticleSaveDraftDao.deleteForumArticleSaveDraft(forumArticleSaveDraft)

    override suspend fun updateForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft) =
        forumArticleSaveDraftDao.updateForumArticleSaveDraft(forumArticleSaveDraft)
}
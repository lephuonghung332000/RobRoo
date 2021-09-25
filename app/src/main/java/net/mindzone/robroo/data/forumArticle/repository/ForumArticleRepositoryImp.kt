package net.mindzone.robroo.data.forumArticle.repository

import androidx.lifecycle.LiveData
import net.mindzone.robroo.data.ResponseCode
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleSaveDraft
import net.mindzone.robroo.data.forumArticle.entity.forumList.ForumListResponse
import net.mindzone.robroo.data.forumArticle.local.ForumArticleSaveDraftDataSource
import net.mindzone.robroo.data.forumArticle.remote.RemoteForumArticleDataSource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class ForumArticleRepositoryImp(
    private val dataSource: RemoteForumArticleDataSource,
    private val forumArticleSaveDraftDataSource: ForumArticleSaveDraftDataSource
) :
    ForumArticleRepository {

    override suspend fun getForumList(): Response<ForumListResponse> = dataSource.getForumList()

    override suspend fun getForumArticles(
        group_id: String,
        searchQuery: String,
        page: Int,
        azure_id: String,
        sort: String,
        owner: Int
    ) = dataSource.getForumArticles(group_id, searchQuery, page, azure_id, sort, owner)

    override suspend fun getForumArticle(article_id: String, azure_id: String) =
        dataSource.getForumArticle(article_id, azure_id)

    override suspend fun getForumArticleComments(article_id: String, azure_id: String) =
        dataSource.getForumArticleComments(article_id, azure_id)

    override suspend fun createForumArticle(
        azure_id: RequestBody,
        tags: List<MultipartBody.Part>,
        images: List<MultipartBody.Part>,
        title: RequestBody,
        text: RequestBody,
        group_id: RequestBody,
        author: RequestBody
    ) = dataSource.createForumArticle(azure_id, tags, images, title, text, group_id, author)

    override suspend fun createForumArticleComment(
        article_id: String,
        azure_id: String,
        parent_id: String,
        text: String,
        author: String
    ) = dataSource.createForumArticleComment(article_id, azure_id, parent_id, text, author)

    override suspend fun likeForumArticleComment(
        article_id: String,
        azure_id: String,
        like: String,
        comment_id: String
    ) = dataSource.likeForumArticleComment(article_id, azure_id, like, comment_id)

    override suspend fun likeForumArticle(
        article_id: String,
        azure_id: String,
        like: String
    ) = dataSource.likeForumArticle(article_id, azure_id, like)

    override suspend fun insertForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft) =
        forumArticleSaveDraftDataSource.insertForumArticleSaveDraft(forumArticleSaveDraft)

    override fun getAllForumArticlesSaveDraft(groupId: String): LiveData<List<ForumArticleSaveDraft>> =
        forumArticleSaveDraftDataSource.getAllForumArticlesSaveDraft(groupId)

    override suspend fun deleteForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft) =
        forumArticleSaveDraftDataSource.deleteForumArticleSaveDraft(forumArticleSaveDraft)

    override suspend fun updateForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft) =
        forumArticleSaveDraftDataSource.updateForumArticleSaveDraft(forumArticleSaveDraft)

    override suspend fun deleteForumArticle(
        azure_id: String,
        article_id: String
    ): Response<ResponseCode> =
        dataSource.deleteForumArticle(azure_id, article_id)


}
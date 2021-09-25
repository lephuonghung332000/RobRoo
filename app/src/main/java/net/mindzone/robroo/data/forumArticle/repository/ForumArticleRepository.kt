package net.mindzone.robroo.data.forumArticle.repository

import androidx.lifecycle.LiveData
import net.mindzone.robroo.data.ResponseCode
import net.mindzone.robroo.data.forumArticle.entity.comment.ForumArticleCommentsResponse
import net.mindzone.robroo.data.forumArticle.entity.createForumArticle.ForumArticleRequest
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleResponse
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleSaveDraft
import net.mindzone.robroo.data.forumArticle.entity.forumArticles.ForumArticlesResponse
import net.mindzone.robroo.data.forumArticle.entity.forumArticles.ResponseData
import net.mindzone.robroo.data.forumArticle.entity.forumList.ForumListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Part
import retrofit2.http.Query

interface ForumArticleRepository {

    suspend fun getForumList(): Response<ForumListResponse>

    suspend fun getForumArticles(
        group_id: String,
        searchQuery: String,
        page: Int,
        azure_id: String,
        sort: String,
        owner: Int
    ): Response<ForumArticlesResponse>

    suspend fun getForumArticle(
        article_id: String,
        azure_id: String
    ): Response<ForumArticleResponse>

    suspend fun getForumArticleComments(
        article_id: String,
        azure_id: String
    ): Response<ForumArticleCommentsResponse>

    suspend fun createForumArticle(
        azure_id: RequestBody,
        tags: List<MultipartBody.Part>,
        images: List<MultipartBody.Part>,
        title: RequestBody,
        text: RequestBody,
        group_id: RequestBody,
        author: RequestBody
    ): Response<ResponseCode>

    suspend fun createForumArticleComment(
        article_id: String,
        azure_id: String,
        parent_id: String,
        text: String,
        author: String
    ): Response<ResponseData>

    suspend fun likeForumArticleComment(
        article_id: String,
        azure_id: String,
        like: String,
        comment_id: String
    ): Response<ResponseData>

    suspend fun likeForumArticle(
        article_id: String,
        azure_id: String,
        like: String,
    ): Response<ResponseCode>

    suspend fun insertForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft): Long
    fun getAllForumArticlesSaveDraft(groupId: String): LiveData<List<ForumArticleSaveDraft>>
    suspend fun deleteForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft)
    suspend fun updateForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft)
    suspend fun deleteForumArticle(azure_id: String,article_id: String ): Response<ResponseCode>
}
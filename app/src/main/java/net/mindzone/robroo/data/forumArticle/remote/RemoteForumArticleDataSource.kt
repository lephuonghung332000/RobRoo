package net.mindzone.robroo.data.forumArticle.remote

import net.mindzone.robroo.data.ResponseCode
import net.mindzone.robroo.data.forumArticle.entity.comment.ForumArticleCommentsResponse
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleResponse
import net.mindzone.robroo.data.forumArticle.entity.forumArticles.ForumArticlesResponse
import net.mindzone.robroo.data.forumArticle.entity.forumArticles.ResponseData
import net.mindzone.robroo.data.forumArticle.entity.forumList.ForumListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface   RemoteForumArticleDataSource {

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
        comment_id: String,
    ): Response<ResponseData>

    suspend fun likeForumArticle(
        article_id: String,
        azure_id: String,
        like: String,
    ): Response<ResponseCode>
    suspend fun deleteForumArticle(azure_id: String,article_id: String):Response<ResponseCode>
}
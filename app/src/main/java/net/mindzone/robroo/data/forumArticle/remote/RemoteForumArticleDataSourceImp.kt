package net.mindzone.robroo.data.forumArticle.remote


import net.mindzone.robroo.data.ResponseCode
import net.mindzone.robroo.data.utils.ApiClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class RemoteForumArticleDataSourceImp(private val apiClient: ApiClient) :
    RemoteForumArticleDataSource {

    override suspend fun getForumList() = apiClient.getForumList()

    override suspend fun getForumArticles(
        group_id: String,
        searchQuery: String,
        page: Int,
        azure_id: String,
        sort: String,
        owner: Int
    ) = apiClient.getForumArticles(group_id, searchQuery, page, azure_id, sort, owner)

    override suspend fun getForumArticle(article_id: String, azure_id: String) =
        apiClient.getForumArticle(article_id, azure_id)


    override suspend fun getForumArticleComments(article_id: String, azure_id: String) =
        apiClient.getForumArticleComments(article_id, azure_id)

    override suspend fun createForumArticle(
        azure_id: RequestBody,
        tags: List<MultipartBody.Part>,
        images: List<MultipartBody.Part>,
        title: RequestBody,
        text: RequestBody,
        group_id: RequestBody,
        author: RequestBody
    )= apiClient.createForumArticle(azure_id, tags, images, title, text, group_id, author)

    override suspend fun createForumArticleComment(
        article_id: String,
        azure_id: String,
        parent_id: String,
        text: String,
        author: String
    ) = apiClient.createForumArticleComment(article_id, azure_id, parent_id, text, author)

    override suspend fun likeForumArticleComment(
        article_id: String,
        azure_id: String,
        like: String,
        comment_id: String,
    ) = apiClient.likeForumArticleComment(article_id, azure_id, like, comment_id)

    override suspend fun likeForumArticle(
        article_id: String,
        azure_id: String,
        like: String
    ) = apiClient.likeForumArticle(article_id, azure_id, like)

    override suspend fun deleteForumArticle(
        azure_id: String,
        article_id: String
    ): Response<ResponseCode> {
        return apiClient.deleteForumArticle(azure_id, article_id)
    }

}
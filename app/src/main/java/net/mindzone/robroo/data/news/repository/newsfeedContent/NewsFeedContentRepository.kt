package net.mindzone.robroo.data.news.repository.newsfeedContent

import net.mindzone.robroo.data.news.entity.newsfeedContent.NewsFeedContentResponse
import retrofit2.Response

interface NewsFeedContentRepository {
    suspend fun getNewsFeedContent(
            azureId: String,
            content_id:Int
    ): Response<NewsFeedContentResponse>
}
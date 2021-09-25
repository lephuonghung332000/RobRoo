package net.mindzone.robroo.data.news.remote.newsfeedContent

import net.mindzone.robroo.data.news.entity.newsfeedContent.NewsFeedContentResponse
import retrofit2.Response

interface RemoteNewsFeedContentDataSource {
    suspend fun getNewsFeedContent(azureId:String,content_id:Int) : Response<NewsFeedContentResponse>
}
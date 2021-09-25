package net.mindzone.robroo.data.news.remote.newsfeedContent

import net.mindzone.robroo.data.news.entity.ListByGroupResponse
import net.mindzone.robroo.data.news.entity.newsfeedContent.NewsFeedContentResponse
import net.mindzone.robroo.data.news.remote.RemoteListByGroupDataSource
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteNewsFeedContentDataSourceImp (val apiClient: ApiClient): RemoteNewsFeedContentDataSource {
    override suspend fun getNewsFeedContent(azureId: String, content_id: Int):
            Response<NewsFeedContentResponse>  = apiClient.getNewsFeedContent(azureId,content_id)

}
package net.mindzone.robroo.data.news.repository.newsfeedContent

import net.mindzone.robroo.data.news.entity.ListByGroupResponse
import net.mindzone.robroo.data.news.entity.newsfeedContent.NewsFeedContentResponse
import net.mindzone.robroo.data.news.remote.RemoteListByGroupDataSource
import net.mindzone.robroo.data.news.remote.newsfeedContent.RemoteNewsFeedContentDataSource
import net.mindzone.robroo.data.news.repository.ListByGroupRepository
import retrofit2.Response

class NewsFeedContentRepositoryImp (val remoteNewsFeedContentDataSource: RemoteNewsFeedContentDataSource) : NewsFeedContentRepository {
    override suspend fun getNewsFeedContent(azureId: String, content_id: Int):
            Response<NewsFeedContentResponse>   = remoteNewsFeedContentDataSource.getNewsFeedContent(azureId,content_id)

}
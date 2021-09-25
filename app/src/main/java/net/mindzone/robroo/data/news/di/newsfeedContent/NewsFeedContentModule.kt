package net.mindzone.robroo.data.news.di.newsfeedContent

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.news.remote.newsfeedContent.RemoteNewsFeedContentDataSource
import net.mindzone.robroo.data.news.remote.newsfeedContent.RemoteNewsFeedContentDataSourceImp
import net.mindzone.robroo.data.news.repository.newsfeedContent.NewsFeedContentRepository
import net.mindzone.robroo.data.news.repository.newsfeedContent.NewsFeedContentRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsFeedContentModule {
    @Provides
    @Singleton
    fun provideRemoteNewsFeedContentDataSource(apiClient: ApiClient): RemoteNewsFeedContentDataSource =
            RemoteNewsFeedContentDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideNewsFeedContentRepository(remoteNewsFeedContentDataSource: RemoteNewsFeedContentDataSource): NewsFeedContentRepository =
            NewsFeedContentRepositoryImp(remoteNewsFeedContentDataSource)
}

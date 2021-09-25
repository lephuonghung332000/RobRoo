package net.mindzone.robroo.data.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.news.remote.RemoteListByGroupDataSource
import net.mindzone.robroo.data.news.remote.RemoteListByGroupDataSourceImp
import net.mindzone.robroo.data.news.repository.ListByGroupRepository
import net.mindzone.robroo.data.news.repository.ListByGroupRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ListByGroupModule {
    @Provides
    @Singleton
    fun provideRemoteListByGroupDataSource(apiClient: ApiClient): RemoteListByGroupDataSource =
            RemoteListByGroupDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideListByGroupRepository(remoteListByGroupDataSource: RemoteListByGroupDataSource): ListByGroupRepository =
            ListByGroupRepositoryImp(remoteListByGroupDataSource)
}

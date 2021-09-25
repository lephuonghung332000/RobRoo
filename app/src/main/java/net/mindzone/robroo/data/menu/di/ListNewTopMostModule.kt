package net.mindzone.robroo.data.menu.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.menu.remote.RemoteListNewsTopMostDataSource
import net.mindzone.robroo.data.menu.remote.RemoteListNewsTopMostDataSourceImp
import net.mindzone.robroo.data.menu.repository.ListNewsTopMostRepository
import net.mindzone.robroo.data.menu.repository.ListNewsTopMostRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ListNewTopMostModule {
    @Provides
    @Singleton
    fun provideRemoteListNewTopMostDataSource(apiClient: ApiClient): RemoteListNewsTopMostDataSource =
            RemoteListNewsTopMostDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideListNewsTopMostRepository(remoteListNewsTopMostDataSource: RemoteListNewsTopMostDataSource): ListNewsTopMostRepository =
            ListNewsTopMostRepositoryImp(remoteListNewsTopMostDataSource)
}
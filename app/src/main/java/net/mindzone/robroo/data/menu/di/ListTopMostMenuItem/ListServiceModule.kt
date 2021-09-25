package net.mindzone.robroo.data.menu.di.ListTopMostMenuItem

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.menu.remote.ListTopMostMenuItem.RemoteListServiceDataSource
import net.mindzone.robroo.data.menu.remote.ListTopMostMenuItem.RemoteListServiceDataSourceImp
import net.mindzone.robroo.data.menu.repository.ListTopMostMenuItem.ListServiceRepository
import net.mindzone.robroo.data.menu.repository.ListTopMostMenuItem.ListServiceRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ListServiceModule {
    @Provides
    @Singleton
    fun provideRemoteListServiceDataSource(apiClient: ApiClient): RemoteListServiceDataSource =
            RemoteListServiceDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideListServiceRepository(remoteListServiceDataSource: RemoteListServiceDataSource): ListServiceRepository =
            ListServiceRepositoryImp(remoteListServiceDataSource)
}
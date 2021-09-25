package net.mindzone.robroo.data.productgroupList.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.productgroupList.remote.RemoteSubGroupDataSource
import net.mindzone.robroo.data.productgroupList.remote.RemoteSubGroupsDataSourceImp
import net.mindzone.robroo.data.productgroupList.repository.SubGroupsRepository
import net.mindzone.robroo.data.productgroupList.repository.SubGroupsReppositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SubGroupsModule {
    @Provides
    @Singleton
    fun provideRemoteSubGroupsDataSource(apiClient: ApiClient): RemoteSubGroupDataSource =
            RemoteSubGroupsDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideSubGroupsRepository(remoteSubGroupDataSource: RemoteSubGroupDataSource): SubGroupsRepository =
            SubGroupsReppositoryImp(remoteSubGroupDataSource)
}
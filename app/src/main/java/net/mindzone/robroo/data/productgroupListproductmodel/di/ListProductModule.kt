package net.mindzone.robroo.data.productgroupListproductmodel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.productgroupListproductmodel.remote.RemoteListProductModelDataSource
import net.mindzone.robroo.data.productgroupListproductmodel.remote.RemoteListProductModelDataSourceImp
import net.mindzone.robroo.data.productgroupListproductmodel.repository.ListProductModelRepository
import net.mindzone.robroo.data.productgroupListproductmodel.repository.ListProductModelRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ListProductModule {
    @Provides
    @Singleton
    fun provideRemoteListProductModelDataSource(apiClient: ApiClient): RemoteListProductModelDataSource =
            RemoteListProductModelDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideListProductModelRepository(remoteListProductModelDataSource: RemoteListProductModelDataSource): ListProductModelRepository =
            ListProductModelRepositoryImp(remoteListProductModelDataSource)
}
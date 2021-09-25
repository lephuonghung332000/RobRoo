package net.mindzone.robroo.data.productmodelListimplement.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.productgroupListproductmodel.remote.RemoteListProductModelDataSource
import net.mindzone.robroo.data.productgroupListproductmodel.remote.RemoteListProductModelDataSourceImp
import net.mindzone.robroo.data.productgroupListproductmodel.repository.ListProductModelRepository
import net.mindzone.robroo.data.productgroupListproductmodel.repository.ListProductModelRepositoryImp
import net.mindzone.robroo.data.productmodelListimplement.remote.RemoteListImplementDataSource
import net.mindzone.robroo.data.productmodelListimplement.remote.RemoteListImplementDataSourceImp
import net.mindzone.robroo.data.productmodelListimplement.repository.ListImplementRepository
import net.mindzone.robroo.data.productmodelListimplement.repository.ListImplementRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ListImplementModule {
    @Provides
    @Singleton
    fun provideRemoteListImplementDataSource(apiClient: ApiClient): RemoteListImplementDataSource =
            RemoteListImplementDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideListImplementRepository(remoteListImplementDataSource: RemoteListImplementDataSource): ListImplementRepository =
            ListImplementRepositoryImp(remoteListImplementDataSource)
}
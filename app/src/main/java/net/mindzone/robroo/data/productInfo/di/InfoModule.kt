package net.mindzone.robroo.data.productInfo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.productInfo.remote.RemoteInfoDataSource
import net.mindzone.robroo.data.productInfo.remote.RemoteInfoDataSourceImp
import net.mindzone.robroo.data.productInfo.repository.InfoRepository
import net.mindzone.robroo.data.productInfo.repository.InfoRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InfoModule {
    @Provides
    @Singleton
    fun provideInfoDataSource(apiClient: ApiClient): RemoteInfoDataSource =
        RemoteInfoDataSourceImp(apiClient)


    @Provides
    @Singleton
    fun provideInfoRepository(remoteInfoDataSource: RemoteInfoDataSource): InfoRepository =
        InfoRepositoryImp(remoteInfoDataSource)
}
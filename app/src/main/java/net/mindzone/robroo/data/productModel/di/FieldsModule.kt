package net.mindzone.robroo.data.productModel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.productModel.remote.RemoteFieldsDataSource
import net.mindzone.robroo.data.productModel.remote.RemoteFieldsDataSourceImp
import net.mindzone.robroo.data.productModel.repository.FieldsRepository
import net.mindzone.robroo.data.productModel.repository.FieldsRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class FieldsModule {
    @Provides
    @Singleton
    fun provideRemoteFieldsDataSource(apiClient: ApiClient): RemoteFieldsDataSource =
        RemoteFieldsDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideFieldsRepository(remoteFieldsDataSource: RemoteFieldsDataSource): FieldsRepository =
        FieldsRepositoryImp(remoteFieldsDataSource)
}
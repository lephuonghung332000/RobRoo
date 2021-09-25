package net.mindzone.robroo.data.user.di

import net.mindzone.robroo.data.user.remote.RemoteUserDataSource
import net.mindzone.robroo.data.user.remote.RemoteUserDataSourceImp
import net.mindzone.robroo.data.utils.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.user.repository.UserRepository
import net.mindzone.robroo.data.user.repository.UserRepositoryImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {
    @Provides
    @Singleton
    fun provideRemoteUserDataSource(apiClient: ApiClient): RemoteUserDataSource =
        RemoteUserDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideContactRepository(remoteUserDataSource: RemoteUserDataSource):UserRepository =
        UserRepositoryImp(remoteUserDataSource)
}
package net.mindzone.robroo.data.token.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.token.remote.RemoteTokenDataSource
import net.mindzone.robroo.data.token.remote.RemoteTokenDataSourceImp
import net.mindzone.robroo.data.token.repository.TokenRepository
import net.mindzone.robroo.data.token.repository.TokenRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class TokenModule {
    @Provides
    @Singleton
    fun provideRemoteTokenDataSource(apiClient: ApiClient): RemoteTokenDataSource =
        RemoteTokenDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideTokenRepository(remoteTokenDataSource: RemoteTokenDataSource): TokenRepository =
        TokenRepositoryImp(remoteTokenDataSource)
}
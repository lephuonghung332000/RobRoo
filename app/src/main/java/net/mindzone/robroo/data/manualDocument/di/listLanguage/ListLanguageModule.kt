package net.mindzone.robroo.data.manualDocument.di.listLanguage

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.manualDocument.remote.listLanguage.RemoteListLanguageDataSource
import net.mindzone.robroo.data.manualDocument.remote.listLanguage.RemoteListLanguageDataSourceImp
import net.mindzone.robroo.data.manualDocument.repository.listLanguage.ListLanguageRepository
import net.mindzone.robroo.data.manualDocument.repository.listLanguage.ListLanguageRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ListLanguageModule {
    @Provides
    @Singleton
    fun provideRemoteListLanguageDataSource(apiClient: ApiClient): RemoteListLanguageDataSource =
            RemoteListLanguageDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideListLanguageRepository(remoteListLanguageDataSource: RemoteListLanguageDataSource): ListLanguageRepository =
            ListLanguageRepositoryImp(remoteListLanguageDataSource)
}
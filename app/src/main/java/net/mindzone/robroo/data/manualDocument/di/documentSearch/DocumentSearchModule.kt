package net.mindzone.robroo.data.manualDocument.di.documentSearch

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.manualDocument.remote.documentSearch.RemoteDocumentSearchDataSource
import net.mindzone.robroo.data.manualDocument.remote.documentSearch.RemoteDocumentSearchDataSourceImp
import net.mindzone.robroo.data.manualDocument.repository.documentSearch.DocumentSearchRepository
import net.mindzone.robroo.data.manualDocument.repository.documentSearch.DocumentSearchRepositoryImp

import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DocumentSearchModule {
    @Provides
    @Singleton
    fun provideRemoteDocumentSearchDataSource(apiClient: ApiClient): RemoteDocumentSearchDataSource =
        RemoteDocumentSearchDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideDocumentSearchRepository(remoteDocumentSearchDataSource: RemoteDocumentSearchDataSource): DocumentSearchRepository =
        DocumentSearchRepositoryImp(remoteDocumentSearchDataSource)
}
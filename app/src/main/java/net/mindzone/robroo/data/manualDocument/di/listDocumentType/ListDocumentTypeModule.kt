package net.mindzone.robroo.data.manualDocument.di.listDocumentType

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.manualDocument.remote.listDocumentType.RemoteListDocumentTypeDataSource
import net.mindzone.robroo.data.manualDocument.remote.listDocumentType.RemoteListDocumentTypeDataSourceImp
import net.mindzone.robroo.data.manualDocument.repository.listDocumentType.ListDocumentTypeRepository
import net.mindzone.robroo.data.manualDocument.repository.listDocumentType.ListDocumentTypeRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ListDocumentTypeModule {
    @Provides
    @Singleton
    fun provideRemoteListDocumentTypeDataSource(apiClient: ApiClient): RemoteListDocumentTypeDataSource =
            RemoteListDocumentTypeDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideListDocumentTypeRepository(remoteListDocumentTypeDataSource: RemoteListDocumentTypeDataSource): ListDocumentTypeRepository =
            ListDocumentTypeRepositoryImp(remoteListDocumentTypeDataSource)
}
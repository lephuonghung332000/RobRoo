package net.mindzone.robroo.data.manualDocument.di.downloadDoc

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.manualDocument.remote.downloadDoc.RemoteDownloadDoc
import net.mindzone.robroo.data.manualDocument.remote.downloadDoc.RemoteDownloadDocImp
import net.mindzone.robroo.data.manualDocument.repository.downloadDoc.DownloadDocRepository
import net.mindzone.robroo.data.manualDocument.repository.downloadDoc.DownloadDocRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DownloadDocModule {
    @Provides
    @Singleton
    fun provideRemoteDownloadDoc(apiClient: ApiClient): RemoteDownloadDoc =
            RemoteDownloadDocImp(apiClient)

    @Provides
    @Singleton
    fun provideDownloadDocRepository(remoteDownloadDoc: RemoteDownloadDoc): DownloadDocRepository =
            DownloadDocRepositoryImp(remoteDownloadDoc)
}
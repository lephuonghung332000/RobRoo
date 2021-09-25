package net.mindzone.robroo.data.manualDocument.di.listDocument

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.manualDocument.local.ItemDAO
import net.mindzone.robroo.data.manualDocument.local.ItemDataSource
import net.mindzone.robroo.data.manualDocument.local.ItemDataSourceImp
import net.mindzone.robroo.data.manualDocument.remote.listDocument.RemoteListDocumentDataSource
import net.mindzone.robroo.data.manualDocument.remote.listDocument.RemoteListDocumentDataSourceImp
import net.mindzone.robroo.data.manualDocument.repository.listDocument.ListDocumentRepository
import net.mindzone.robroo.data.manualDocument.repository.listDocument.ListDocumentRepositoryImp
import net.mindzone.robroo.data.manualDocument.repository.local.ItemRepository
import net.mindzone.robroo.data.manualDocument.repository.local.ItemRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import net.mindzone.robroo.data.utils.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ListDocumentModule {
    @Provides
    @Singleton
    fun provideRemoteListDocumentDataSource(apiClient: ApiClient): RemoteListDocumentDataSource =
            RemoteListDocumentDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideListDocumentRepository(remoteListDocumentDataSource: RemoteListDocumentDataSource): ListDocumentRepository =
            ListDocumentRepositoryImp(remoteListDocumentDataSource)

    @Provides
    @Singleton
    fun provideItems(appDatabase: AppDatabase) = appDatabase.mItemsDao


    @Provides
    @Singleton
    fun provideItemstaSource(ItemsDAO: ItemDAO) : ItemDataSource = ItemDataSourceImp(ItemsDAO)

    @Provides
    @Singleton
    fun provideItemsRepository(ItemsDataSource: ItemDataSource) : ItemRepository = ItemRepositoryImp(ItemsDataSource)


}
package net.mindzone.robroo.data.faqList.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.faqList.remote.RemoteFaqListDataSource
import net.mindzone.robroo.data.faqList.remote.RemoteFaqListDataSourceImp
import net.mindzone.robroo.data.faqList.repository.FaqListRepository
import net.mindzone.robroo.data.faqList.repository.FaqListRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FaqListModule {
    @Provides
    @Singleton
    fun provideRemoteFaqListDataSource(apiClient: ApiClient): RemoteFaqListDataSource =
        RemoteFaqListDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideFaqListRepository(remoteFaqListDataSource: RemoteFaqListDataSource): FaqListRepository =
        FaqListRepositoryImp(remoteFaqListDataSource)
}
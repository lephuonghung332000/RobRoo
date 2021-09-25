package net.mindzone.robroo.data.faqArticle.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.faqArticle.remote.RemoteFaqArticleDataSource
import net.mindzone.robroo.data.faqArticle.remote.RemoteFaqArticleDataSourceImp
import net.mindzone.robroo.data.faqArticle.repository.FaqArticleRepository
import net.mindzone.robroo.data.faqArticle.repository.FaqArticleRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FaqArticleModule {
    @Provides
    @Singleton
    fun provideRemoteFaqArticleDataSource(apiClient: ApiClient): RemoteFaqArticleDataSource =
        RemoteFaqArticleDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideFaqArticleRepository(remoteFaqArticleDataSource: RemoteFaqArticleDataSource): FaqArticleRepository =
        FaqArticleRepositoryImp(remoteFaqArticleDataSource)
}
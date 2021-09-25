package net.mindzone.robroo.data.forumArticle.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.forumArticle.local.ForumArticleSaveDraftDao
import net.mindzone.robroo.data.forumArticle.local.ForumArticleSaveDraftDataSource
import net.mindzone.robroo.data.forumArticle.local.ForumArticleSaveDraftDataSourceImp
import net.mindzone.robroo.data.forumArticle.remote.RemoteForumArticleDataSource
import net.mindzone.robroo.data.forumArticle.remote.RemoteForumArticleDataSourceImp
import net.mindzone.robroo.data.forumArticle.repository.ForumArticleRepository
import net.mindzone.robroo.data.forumArticle.repository.ForumArticleRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import net.mindzone.robroo.data.utils.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ForumArticleModule {
    @Provides
    @Singleton
    fun provideRemoteForumArticleModule(apiClient: ApiClient): RemoteForumArticleDataSource =
        RemoteForumArticleDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideForumArticleRepository(
        remoteForumArticleDataSource: RemoteForumArticleDataSource,
        forumArticleSaveDraftDataSource: ForumArticleSaveDraftDataSource
    ): ForumArticleRepository =
        ForumArticleRepositoryImp(remoteForumArticleDataSource, forumArticleSaveDraftDataSource)

    @Provides
    @Singleton
    fun provideForumArticleSaveDraftDao(appDatabase: AppDatabase) = appDatabase.forumArticleSaveDraftDao()

    @Provides
    @Singleton
    fun provideForumArticleSaveDraftDataSource(forumArticleSaveDraftDao: ForumArticleSaveDraftDao): ForumArticleSaveDraftDataSource =
        ForumArticleSaveDraftDataSourceImp(forumArticleSaveDraftDao)


}
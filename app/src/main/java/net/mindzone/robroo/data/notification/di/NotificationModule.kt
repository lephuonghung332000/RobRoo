package net.mindzone.robroo.data.notification.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.notification.remote.RemoteNotificationDataSource
import net.mindzone.robroo.data.notification.remote.RemoteNotificationDataSourceImp
import net.mindzone.robroo.data.notification.repository.NotificationRepository
import net.mindzone.robroo.data.notification.repository.NotificationRepositoryImp

import net.mindzone.robroo.data.user.remote.RemoteUserDataSource
import net.mindzone.robroo.data.user.repository.UserRepository
import net.mindzone.robroo.data.user.repository.UserRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NotificationModule {
    @Provides
    @Singleton
    fun provideRemoteNotificationDataSource(apiClient: ApiClient): RemoteNotificationDataSource =
        RemoteNotificationDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideNotificationRepository(remoteNotificationDataSource: RemoteNotificationDataSource): NotificationRepository =
        NotificationRepositoryImp(remoteNotificationDataSource)
}
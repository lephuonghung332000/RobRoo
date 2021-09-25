package net.mindzone.robroo.data.applicationSetting.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.applicationSetting.remote.RemoteAppliationSettingDataSource
import net.mindzone.robroo.data.applicationSetting.remote.RemoteApplicationSettingDataSourceImp
import net.mindzone.robroo.data.applicationSetting.repository.ApplicationSettingRepository
import net.mindzone.robroo.data.applicationSetting.repository.ApplicationSettingRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationSettingModule {
    @Provides
    @Singleton
    fun provideRemoteApplicationSettingDataSource(apiClient: ApiClient): RemoteAppliationSettingDataSource =
        RemoteApplicationSettingDataSourceImp(apiClient)

    @Provides
    @Singleton
    fun provideApplicationSettingRepository(remoteApplicationSettingDataSource: RemoteAppliationSettingDataSource): ApplicationSettingRepository =
        ApplicationSettingRepositoryImp(remoteApplicationSettingDataSource)
}
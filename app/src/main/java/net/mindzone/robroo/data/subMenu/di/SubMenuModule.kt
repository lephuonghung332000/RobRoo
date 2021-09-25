package net.mindzone.robroo.data.subMenu.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.mindzone.robroo.data.applicationSetting.remote.RemoteAppliationSettingDataSource
import net.mindzone.robroo.data.applicationSetting.repository.ApplicationSettingRepository
import net.mindzone.robroo.data.applicationSetting.repository.ApplicationSettingRepositoryImp
import net.mindzone.robroo.data.subMenu.local.SubMenuDAO
import net.mindzone.robroo.data.subMenu.local.SubMenuDataSource
import net.mindzone.robroo.data.subMenu.local.SubMenuDataSourceImp
import net.mindzone.robroo.data.subMenu.remote.RemoteSubMenuDataSource
import net.mindzone.robroo.data.subMenu.remote.RemoteSubMenuDataSourceImp
import net.mindzone.robroo.data.subMenu.repository.SubMenuRepository
import net.mindzone.robroo.data.subMenu.repository.SubMenuRepositoryImp
import net.mindzone.robroo.data.utils.ApiClient
import net.mindzone.robroo.data.utils.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SubMenuModule {
    @Provides
    @Singleton
    fun provideSubMenuDao(appDatabase: AppDatabase) = appDatabase.mSubMenuDao


    @Provides
    @Singleton
    fun provideSubMenuDataSource(subMenuDAO: SubMenuDAO) : SubMenuDataSource  = SubMenuDataSourceImp(subMenuDAO)

    @Provides
    @Singleton
    fun provideRemoteSubMenuDataSource(apiClient: ApiClient) : RemoteSubMenuDataSource{
        return RemoteSubMenuDataSourceImp(apiClient)
    }

    @Provides
    @Singleton
    fun provideSubMenuRepository(subMenuDataSource: SubMenuDataSource, remoteSubMenuDataSource: RemoteSubMenuDataSource) : SubMenuRepository = SubMenuRepositoryImp(subMenuDataSource,remoteSubMenuDataSource)

}
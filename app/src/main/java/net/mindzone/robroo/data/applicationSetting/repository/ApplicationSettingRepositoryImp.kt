package net.mindzone.robroo.data.applicationSetting.repository

import net.mindzone.robroo.data.applicationSetting.entity.ApplicationResponse
import net.mindzone.robroo.data.applicationSetting.remote.RemoteAppliationSettingDataSource
import retrofit2.Response

class ApplicationSettingRepositoryImp (val remoteAppliationSettingDataSource: RemoteAppliationSettingDataSource) :
    ApplicationSettingRepository {
    override suspend fun getApplicationSetting(): Response<ApplicationResponse> =
        remoteAppliationSettingDataSource.getApplicationSetting()
}
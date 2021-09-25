package net.mindzone.robroo.data.applicationSetting.remote

import net.mindzone.robroo.data.applicationSetting.entity.ApplicationResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteApplicationSettingDataSourceImp(val apiClient: ApiClient) : RemoteAppliationSettingDataSource{
    override suspend fun getApplicationSetting(): Response<ApplicationResponse> {
        return apiClient.getApplicationSettings()
    }


}
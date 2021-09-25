package net.mindzone.robroo.data.manualDocument.remote.listLanguage

import net.mindzone.robroo.data.manualDocument.entity.listLanguage.ListLanguageResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteListLanguageDataSourceImp (val apiClient: ApiClient): RemoteListLanguageDataSource {
    override suspend fun getListLanguage(azureId: String):
            Response<ListLanguageResponse>  = apiClient.getListLanguage(azureId)

}
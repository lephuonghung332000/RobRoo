package net.mindzone.robroo.data.manualDocument.remote.listDocumentType

import net.mindzone.robroo.data.manualDocument.entity.listDocumentType.ListDocumentTypeResponse
import net.mindzone.robroo.data.manualDocument.entity.listLanguage.ListLanguageResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteListDocumentTypeDataSourceImp (val apiClient: ApiClient): RemoteListDocumentTypeDataSource {
    override suspend fun getListDocumentType(azureId: String):
            Response<ListDocumentTypeResponse> = apiClient.getListDocumentType(azureId)


}
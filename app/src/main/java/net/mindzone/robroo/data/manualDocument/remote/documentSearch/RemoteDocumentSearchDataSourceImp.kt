package net.mindzone.robroo.data.manualDocument.remote.documentSearch

import net.mindzone.robroo.data.manualDocument.entity.documentSearch.DocumentSearchResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteDocumentSearchDataSourceImp (val apiClient: ApiClient): RemoteDocumentSearchDataSource {
    override suspend fun getDocumentSearch(azureId: String,model_id:String,):
            Response<DocumentSearchResponse> = apiClient.getDocumentSearch(azureId,model_id)
}
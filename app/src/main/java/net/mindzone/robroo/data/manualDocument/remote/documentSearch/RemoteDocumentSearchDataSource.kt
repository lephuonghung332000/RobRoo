package net.mindzone.robroo.data.manualDocument.remote.documentSearch

import net.mindzone.robroo.data.manualDocument.entity.documentSearch.DocumentSearchResponse
import retrofit2.Response

interface RemoteDocumentSearchDataSource {
    suspend fun getDocumentSearch(azureId:String,model_id:String) : Response<DocumentSearchResponse>
}

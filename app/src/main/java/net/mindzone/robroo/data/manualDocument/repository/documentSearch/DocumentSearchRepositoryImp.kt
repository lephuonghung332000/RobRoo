package net.mindzone.robroo.data.manualDocument.repository.documentSearch

import net.mindzone.robroo.data.manualDocument.entity.documentSearch.DocumentSearchResponse
import net.mindzone.robroo.data.manualDocument.remote.documentSearch.RemoteDocumentSearchDataSource
import retrofit2.Response

class DocumentSearchRepositoryImp(val remoteDocumentSearchDataSource: RemoteDocumentSearchDataSource) :
    DocumentSearchRepository {
    override suspend fun getDocumentSearch(azureId: String,model_id:String):
            Response<DocumentSearchResponse> = remoteDocumentSearchDataSource.getDocumentSearch(azureId,model_id)


}

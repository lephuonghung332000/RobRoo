package net.mindzone.robroo.data.manualDocument.repository.documentSearch

import net.mindzone.robroo.data.manualDocument.entity.documentSearch.DocumentSearchResponse
import retrofit2.Response

interface DocumentSearchRepository {
    suspend fun getDocumentSearch(
        azureId: String,
        model_id:String
    ): Response<DocumentSearchResponse>
}
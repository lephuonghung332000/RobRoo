package net.mindzone.robroo.data.manualDocument.repository.listDocumentType

import net.mindzone.robroo.data.manualDocument.entity.listDocumentType.ListDocumentTypeResponse
import retrofit2.Response

interface ListDocumentTypeRepository {
    suspend fun getListDocumentType(
            azureId: String,
    ): Response<ListDocumentTypeResponse>
}
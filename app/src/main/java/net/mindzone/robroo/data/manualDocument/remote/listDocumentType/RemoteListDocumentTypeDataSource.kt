package net.mindzone.robroo.data.manualDocument.remote.listDocumentType

import net.mindzone.robroo.data.manualDocument.entity.listDocumentType.ListDocumentTypeResponse
import retrofit2.Response

interface RemoteListDocumentTypeDataSource {
    suspend fun getListDocumentType(azureId:String) : Response<ListDocumentTypeResponse>
}
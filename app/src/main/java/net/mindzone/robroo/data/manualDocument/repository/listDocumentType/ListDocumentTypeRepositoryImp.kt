package net.mindzone.robroo.data.manualDocument.repository.listDocumentType

import net.mindzone.robroo.data.manualDocument.entity.listDocumentType.ListDocumentTypeResponse
import net.mindzone.robroo.data.manualDocument.remote.listDocumentType.RemoteListDocumentTypeDataSource

import retrofit2.Response

class ListDocumentTypeRepositoryImp (val remoteListDocumentTypeDataSource: RemoteListDocumentTypeDataSource) : ListDocumentTypeRepository {
    override suspend fun getListDocumentType(azureId: String):
            Response<ListDocumentTypeResponse> = remoteListDocumentTypeDataSource.getListDocumentType(azureId)


}
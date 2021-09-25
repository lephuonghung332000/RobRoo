package net.mindzone.robroo.data.manualDocument.repository.listDocument

import net.mindzone.robroo.data.manualDocument.entity.listDocument.ListDocumentResponse
import net.mindzone.robroo.data.manualDocument.remote.listDocument.RemoteListDocumentDataSource
import retrofit2.Response

class ListDocumentRepositoryImp (private val remoteListDocumentDataSource: RemoteListDocumentDataSource) : ListDocumentRepository {
    override suspend fun getListDocument(azureId: String, start:Int, size:Int, model_id:String,sort:String, doclangid:Int?, doctypeid:Int?): Response<ListDocumentResponse> = remoteListDocumentDataSource.getListDocument(
                azureId,
                start,
                size,
                model_id,
                sort,
                doclangid,
                doctypeid
            )
}
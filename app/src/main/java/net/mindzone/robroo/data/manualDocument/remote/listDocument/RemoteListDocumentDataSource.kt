package net.mindzone.robroo.data.manualDocument.remote.listDocument

import net.mindzone.robroo.data.manualDocument.entity.listDocument.ListDocumentResponse
import retrofit2.Response
interface RemoteListDocumentDataSource {
    suspend fun getListDocument(azureId:String, start:Int, size:Int, model_id:String,sort:String,doclangid:Int?,doctypeid:Int?) : Response<ListDocumentResponse>
}
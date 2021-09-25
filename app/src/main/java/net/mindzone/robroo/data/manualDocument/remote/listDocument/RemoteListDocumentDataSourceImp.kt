package net.mindzone.robroo.data.manualDocument.remote.listDocument

import net.mindzone.robroo.data.manualDocument.entity.listDocument.ListDocumentResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteListDocumentDataSourceImp (val apiClient: ApiClient): RemoteListDocumentDataSource {
    override suspend fun getListDocument(azureId: String, start:Int, size:Int, model_id:String,sort:String,doclangid:Int?,doctypeid:Int?):
          Response<ListDocumentResponse> = apiClient.getListDocument(azureId,start,size,model_id,sort,doclangid,doctypeid)
}


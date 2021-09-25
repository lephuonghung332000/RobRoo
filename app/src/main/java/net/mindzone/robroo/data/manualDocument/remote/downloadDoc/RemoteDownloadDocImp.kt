package net.mindzone.robroo.data.manualDocument.remote.downloadDoc

import net.mindzone.robroo.data.utils.ApiClient
import okhttp3.ResponseBody
import retrofit2.Response

class RemoteDownloadDocImp (val apiClient: ApiClient): RemoteDownloadDoc {
    override suspend fun getDownloadDoc(azureId: String, downloadUrl: String):
            Response<ResponseBody> = apiClient.downloadPdf(azureId,downloadUrl)


}
package net.mindzone.robroo.data.manualDocument.repository.downloadDoc

import net.mindzone.robroo.data.manualDocument.remote.downloadDoc.RemoteDownloadDoc
import okhttp3.ResponseBody
import retrofit2.Response

class DownloadDocRepositoryImp (private val remoteDownloadDoc: RemoteDownloadDoc) : DownloadDocRepository {
    override suspend fun getDownloadDoc(azureId: String, downloadUrl: String):
            Response<ResponseBody> = remoteDownloadDoc.getDownloadDoc(azureId,downloadUrl)

}
package net.mindzone.robroo.data.manualDocument.repository.downloadDoc

import okhttp3.ResponseBody
import retrofit2.Response

interface DownloadDocRepository {
    suspend fun getDownloadDoc(
        azureId: String,
        downloadUrl :String
    ): Response<ResponseBody>
}
package net.mindzone.robroo.data.manualDocument.remote.downloadDoc

import okhttp3.ResponseBody
import retrofit2.Response

interface RemoteDownloadDoc {
    suspend fun getDownloadDoc(azureId:String, downloadUrl:String) : Response<ResponseBody>
}
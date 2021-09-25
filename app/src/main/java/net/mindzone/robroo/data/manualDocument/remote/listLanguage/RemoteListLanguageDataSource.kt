package net.mindzone.robroo.data.manualDocument.remote.listLanguage

import net.mindzone.robroo.data.manualDocument.entity.listLanguage.ListLanguageResponse
import retrofit2.Response

interface RemoteListLanguageDataSource {
    suspend fun getListLanguage(azureId:String) : Response<ListLanguageResponse>
}
package net.mindzone.robroo.data.manualDocument.repository.listLanguage
import net.mindzone.robroo.data.manualDocument.entity.listLanguage.ListLanguageResponse
import retrofit2.Response

interface ListLanguageRepository {
    suspend fun getListLanguage(
            azureId: String,
    ): Response<ListLanguageResponse>
}
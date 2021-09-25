package net.mindzone.robroo.data.manualDocument.repository.listLanguage
import net.mindzone.robroo.data.manualDocument.entity.listLanguage.ListLanguageResponse
import net.mindzone.robroo.data.manualDocument.remote.listLanguage.RemoteListLanguageDataSource
import retrofit2.Response

class ListLanguageRepositoryImp (val remoteListLanguageDataSource: RemoteListLanguageDataSource) : ListLanguageRepository {
    override suspend fun getListLanguage(azureId: String):
            Response<ListLanguageResponse> = remoteListLanguageDataSource.getListLanguage(azureId)

}
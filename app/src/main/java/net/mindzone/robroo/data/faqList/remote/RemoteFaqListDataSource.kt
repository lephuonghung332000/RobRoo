package net.mindzone.robroo.data.faqList.remote


import net.mindzone.robroo.data.faqList.entity.FaqListResponse
import retrofit2.Response

interface RemoteFaqListDataSource {
    suspend fun getFaqList(section: String ) : Response<FaqListResponse>
}
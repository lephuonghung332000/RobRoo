package net.mindzone.robroo.data.faqList.remote

import net.mindzone.robroo.data.faqList.entity.FaqListResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteFaqListDataSourceImp(val apiClient: ApiClient) : RemoteFaqListDataSource {
    override suspend fun getFaqList(section: String): Response<FaqListResponse> = apiClient.getFaqList(section)
}
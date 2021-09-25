package net.mindzone.robroo.data.faqArticle.remote

import net.mindzone.robroo.data.faqArticle.entity.FaqArticleResponse
import net.mindzone.robroo.data.utils.ApiClient
import retrofit2.Response

class RemoteFaqArticleDataSourceImp (val apiClient: ApiClient): RemoteFaqArticleDataSource {
    override suspend fun getFaqArticle(faq_id: String): Response<FaqArticleResponse> = apiClient.getFAQArticle(faq_id)
}
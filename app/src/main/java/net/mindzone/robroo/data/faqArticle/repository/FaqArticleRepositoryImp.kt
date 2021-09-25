package net.mindzone.robroo.data.faqArticle.repository

import net.mindzone.robroo.data.faqArticle.entity.FaqArticleResponse
import net.mindzone.robroo.data.faqArticle.remote.RemoteFaqArticleDataSource
import retrofit2.Response

class FaqArticleRepositoryImp (val remoteFaqArticleDataSource: RemoteFaqArticleDataSource) : FaqArticleRepository {
    override suspend fun getFaqArticle(faq_id: String): Response<FaqArticleResponse> = remoteFaqArticleDataSource.getFaqArticle(faq_id)
}
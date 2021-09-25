package net.mindzone.robroo.data.faqArticle.remote

import net.mindzone.robroo.data.faqArticle.entity.FaqArticleResponse
import retrofit2.Response

interface RemoteFaqArticleDataSource {
    suspend fun getFaqArticle(faq_id: String ) : Response<FaqArticleResponse>
}
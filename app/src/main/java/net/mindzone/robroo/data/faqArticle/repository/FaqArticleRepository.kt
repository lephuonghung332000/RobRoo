package net.mindzone.robroo.data.faqArticle.repository

import net.mindzone.robroo.data.faqArticle.entity.FaqArticleResponse
import retrofit2.Response

interface FaqArticleRepository {
    suspend fun getFaqArticle(faq_id: String) : Response<FaqArticleResponse>
}
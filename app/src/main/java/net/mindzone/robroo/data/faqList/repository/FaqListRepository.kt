package net.mindzone.robroo.data.faqList.repository

import net.mindzone.robroo.data.faqList.entity.FaqListResponse
import retrofit2.Response

interface FaqListRepository {
    suspend fun getFaqList(section: String) : Response<FaqListResponse>
}
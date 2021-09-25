package net.mindzone.robroo.data.faqList.repository

import net.mindzone.robroo.data.faqList.entity.FaqListResponse
import net.mindzone.robroo.data.faqList.remote.RemoteFaqListDataSource
import retrofit2.Response

class FaqListRepositoryImp(val remoteFaqListDataSource: RemoteFaqListDataSource) : FaqListRepository {
    override suspend fun getFaqList(section: String): Response<FaqListResponse> = remoteFaqListDataSource.getFaqList(section)
}
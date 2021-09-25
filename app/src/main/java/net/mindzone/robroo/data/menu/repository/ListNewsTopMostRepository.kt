package net.mindzone.robroo.data.menu.repository

import net.mindzone.robroo.data.menu.entity.listNewUnderTopMostLevel.ListNewsTopmostResponse
import retrofit2.Response

interface ListNewsTopMostRepository {
    suspend fun getListNewsTopMost(
            azureId: String,
    ): Response<ListNewsTopmostResponse>
}
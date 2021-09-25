package net.mindzone.robroo.data.manualDocument.repository.local

import androidx.lifecycle.LiveData
import net.mindzone.robroo.data.manualDocument.entity.listDocument.Items

interface ItemRepository {
    suspend fun insert (items: Items) : Long
    suspend fun update (items: Items)
    suspend fun  delete(docnumber:String)
    fun getAllItems(): LiveData<List<Items>>

}
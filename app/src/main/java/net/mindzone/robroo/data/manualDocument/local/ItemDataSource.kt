package net.mindzone.robroo.data.manualDocument.local

import androidx.lifecycle.LiveData
import net.mindzone.robroo.data.manualDocument.entity.listDocument.Items

interface ItemDataSource {
    suspend fun insert (item: Items) : Long
    suspend fun update (item: Items)
    suspend fun delete (docnumber:String)
    fun getAllItem (): LiveData<List<Items>>

}
package net.mindzone.robroo.data.manualDocument.local

import androidx.lifecycle.LiveData
import net.mindzone.robroo.data.manualDocument.entity.listDocument.Items

class ItemDataSourceImp internal constructor(private val itemDao: ItemDAO):
    ItemDataSource {
    override suspend fun insert(item: Items): Long {
        return itemDao.insert(item)
    }

    override suspend fun update(item: Items) {
        itemDao.insert(item)
    }
    override suspend fun delete(docnumber:String) {
        itemDao.delete(docnumber)
    }

    override fun getAllItem(): LiveData<List<Items>> {
        return itemDao.getAllItemPdf()
    }
}
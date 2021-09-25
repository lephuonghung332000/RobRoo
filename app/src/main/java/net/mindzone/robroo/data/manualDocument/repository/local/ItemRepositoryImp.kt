package net.mindzone.robroo.data.manualDocument.repository.local

import androidx.lifecycle.LiveData
import net.mindzone.robroo.data.manualDocument.entity.listDocument.Items
import net.mindzone.robroo.data.manualDocument.local.ItemDataSource

class ItemRepositoryImp(private val dataSource: ItemDataSource) : ItemRepository {
    override suspend fun insert(items: Items): Long {
        return dataSource.insert(items)
    }

    override suspend fun update(items: Items) {
        dataSource.update(items)
    }

    override suspend fun delete(docnumber:String) {
        dataSource.delete(docnumber)
    }

    override fun getAllItems(): LiveData<List<Items>> {
      return dataSource.getAllItem()
    }
}
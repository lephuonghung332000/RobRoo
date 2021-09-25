package net.mindzone.robroo.data.manualDocument.local

import androidx.lifecycle.LiveData
import androidx.room.*
import net.mindzone.robroo.data.manualDocument.entity.listDocument.Items

@Dao
interface ItemDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert( items: Items) : Long

    @Update
    suspend fun update( items: Items)

    @Query("DELETE FROM itempdf WHERE  docnumber LIKE :docnumber")
    suspend fun delete(docnumber:String)

    @Query("SELECT * FROM itempdf ORDER BY id DESC")
    fun getAllItemPdf(): LiveData<List<Items>>
}
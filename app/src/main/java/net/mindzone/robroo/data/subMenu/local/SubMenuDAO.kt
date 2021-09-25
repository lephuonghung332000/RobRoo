package net.mindzone.robroo.data.subMenu.local


import androidx.lifecycle.LiveData
import androidx.room.*
import net.mindzone.robroo.data.subMenu.entity.SubMenu

@Dao
interface SubMenuDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert( subMenu: SubMenu) : Long


    @Update
    suspend fun update( subMenu: SubMenu)

    @Update
    suspend fun update( items: List<SubMenu>)

    @Delete
    suspend fun delete(subMenu: SubMenu)

    @Query("SELECT * FROM subMenu ORDER by positionAllSubMenu")
    fun getAllSubMenu(): LiveData<MutableList<SubMenu>>

    @Query("SELECT * FROM subMenu WHERE selected = 1 ORDER by positionAllSubMenu")
    fun getSubMenuIsChecked(): LiveData<MutableList<SubMenu>>
}
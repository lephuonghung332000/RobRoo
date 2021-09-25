package net.mindzone.robroo.data.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleSaveDraft
import net.mindzone.robroo.data.forumArticle.local.ForumArticleSaveDraftDao
import net.mindzone.robroo.data.manualDocument.entity.listDocument.Items
import net.mindzone.robroo.data.manualDocument.local.ItemDAO
import net.mindzone.robroo.data.subMenu.entity.SubMenu
import net.mindzone.robroo.data.subMenu.local.SubMenuDAO

@Database(
    entities = [ForumArticleSaveDraft::class, SubMenu::class, Items::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun forumArticleSaveDraftDao(): ForumArticleSaveDraftDao
    abstract val mSubMenuDao : SubMenuDAO
    abstract val mItemsDao: ItemDAO


    companion object {
        const val DB_NAME = "AppDatabase"

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }

//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        @Synchronized
//        fun getDataBase(context: Context): AppDatabase {
//            val tempInstance = INSTANCE
//            if (tempInstance != null) {
//                return tempInstance
//            }
//            synchronized(this) {
//                val instance =
//                    Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,
//                        DB_NAME)// Wipes and rebuilds instead of migrating if no Migration object.
//                // Migration is not part of this codelab.
//                        .allowMainThreadQueries()
//                        .build()
//                INSTANCE = instance
//                return instance
//            }
//
//        }
    }
}
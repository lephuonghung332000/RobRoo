package net.mindzone.robroo.data.forumArticle.local

import androidx.lifecycle.LiveData
import androidx.room.*
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleSaveDraft

@Dao
interface ForumArticleSaveDraftDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft): Long

    @Query("SELECT * FROM forumArticles WHERE groupId LIKE :groupId ORDER BY forumArticleId DESC")
    fun getAllForumArticlesSaveDraft(groupId: String): LiveData<List<ForumArticleSaveDraft>>

    @Delete
    suspend fun deleteForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft)

    @Update(entity = ForumArticleSaveDraft::class)
    suspend fun updateForumArticleSaveDraft(forumArticleSaveDraft: ForumArticleSaveDraft)

//    @Query("SELECT * FROM contacts ORDER BY name ASC")
//    fun getAllContactsAsPage(): PagingSource<Int, Contact>
}
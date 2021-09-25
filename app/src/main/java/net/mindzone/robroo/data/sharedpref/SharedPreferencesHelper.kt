package net.mindzone.robroo.data.sharedpref

import androidx.lifecycle.MutableLiveData
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleSaveSharef

interface SharedPreferencesHelper {
    val currentUserId: MutableLiveData<String>
    fun setCurrentUserId(storeId: String)
    fun getCurrentUserId(): String
    fun clearCurrentUserId()


    fun setCurrentIdToken(token: String)
    fun getCurrentIdToken(): String
    fun setStatusNotification(id: String)
    fun getStatusNotification(): String
    fun setFcmToken(Token:String)
    fun getFcmToken():String

    fun setForumArticleSaveLocal(forumArticleSaveSharef: ForumArticleSaveSharef)
    fun getForumArticleSaveLocal():ForumArticleSaveSharef
    fun deleteForumArticleSaveLocal()

}
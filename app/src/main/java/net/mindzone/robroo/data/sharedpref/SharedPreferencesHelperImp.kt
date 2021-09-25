package net.mindzone.robroo.data.sharedpref

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import net.mindzone.robroo.data.forumArticle.entity.forumArticle.ForumArticleSaveSharef
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesHelperImp @Inject constructor(private val sharedPreferences: SharedPreferences) :
    SharedPreferencesHelper {

    override val currentUserId: MutableLiveData<String> = MutableLiveData(getCurrentUserId())

    override fun getCurrentUserId(): String =

        sharedPreferences.getString(PREF_USER_ID, "") ?: ""

    override fun clearCurrentUserId() {
        sharedPreferences.edit().remove(PREF_USER_ID).apply()
    }

    override fun setCurrentUserId(userId: String) {
        sharedPreferences.edit().apply {
            putString(PREF_USER_ID, userId)
        }.apply()
        this.currentUserId.postValue(userId)
    }

    override fun setCurrentIdToken(token: String) {
        sharedPreferences.edit().apply {
            putString(SHARED_PREFERENCES_MERCHANT_ID_TOKEN, token)
        }.apply()
    }

    override fun getCurrentIdToken(): String =
        sharedPreferences.getString(SHARED_PREFERENCES_MERCHANT_ID_TOKEN, "") ?: ""

    override fun setStatusNotification(id: String) {
        sharedPreferences.edit().apply{
            putString(PREF_CODE_NOTIFICATION,id)
        }.apply()
    }

    override fun getStatusNotification() : String = sharedPreferences.getString(PREF_CODE_NOTIFICATION,"1") ?:"1"
    override fun setFcmToken(token: String) {
        sharedPreferences.edit().apply {
            putString(PREF_FCM_TOKEN, token)
        }.apply()
    }

    override fun getFcmToken(): String =
        sharedPreferences.getString(PREF_FCM_TOKEN, "") ?:""

    override fun setForumArticleSaveLocal(forumArticleSaveSharef: ForumArticleSaveSharef) {
        var gson : Gson = Gson()
        var stringJson = gson.toJson(forumArticleSaveSharef)
        sharedPreferences.edit().apply{
            putString(PREF_FORUM_ARTICLE, stringJson).apply()
        }
    }

    override fun getForumArticleSaveLocal(): ForumArticleSaveSharef {
        val stringJson = sharedPreferences.getString(PREF_FORUM_ARTICLE, " {    \n" +
                "    \"ForumArticleSaveSharef\": {    \n" +
                "        \"title\":       \"\",     \n" +
                "        \"text\":      \"\"  \n" +
                "        \n" +
                "    }    \n" +
                "}  ")
        val gson = Gson()
        val forumArticle  = gson.fromJson(stringJson, ForumArticleSaveSharef::class.java)
        return forumArticle

    }

    override fun deleteForumArticleSaveLocal() {
        sharedPreferences.edit().remove(PREF_FORUM_ARTICLE).apply()
    }


    companion object {
        const val PREF_FCM_TOKEN = "fcm_token"
        const val PREF_FORUM_ARTICLE = "forum_article"
        const val PREF_CODE_NOTIFICATION = "code"
        const val PREF_USER_ID = "store_id"
        private const val SHARED_PREFERENCES_MERCHANT_ID_TOKEN = "MerchantIdToken"

    }
}

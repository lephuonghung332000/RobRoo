package net.mindzone.robroo.data.sharedpref.sharePreferencesComment

import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

class SharedPreferencesCommentHelperImp @Inject constructor(private val sharedPreferences: SharedPreferences) : SharedPreferencesCommentHelper{
    override fun setComment(key: String, value: String) {
        sharedPreferences.edit().putString(key,value).apply()
    }

    override fun getComment(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    override fun deleteSharePrefComment(key: String) {
        sharedPreferences
        sharedPreferences.edit().remove(key).apply()
    }
    
}
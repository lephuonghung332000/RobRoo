package net.mindzone.robroo.data.sharedpref.sharePreferencesComment

interface SharedPreferencesCommentHelper{
    fun setComment(key:String, value: String)

    fun getComment(key: String): String

    fun deleteSharePrefComment(key: String)
}
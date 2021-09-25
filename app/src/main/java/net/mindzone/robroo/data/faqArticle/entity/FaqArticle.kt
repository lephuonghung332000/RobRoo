package net.mindzone.robroo.data.faqArticle.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FaqArticle (
    @SerializedName("faq_id")
    val faq_id: String  = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("desc")
    val desc: String = "",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("images")
    val images: List<String>
    ) : Serializable

class Image()
package net.mindzone.robroo.data.faqList.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FaqList(
    @SerializedName("faq_id")
    val faq_id: String  = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("icon")
    val icon: String = "",

): Serializable
package net.mindzone.robroo.data.forumArticle.entity.forumArticles

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.R
import java.io.Serializable

data class ForumArticles(
    @SerializedName("article_id")
    val articleId:String,
    @SerializedName("title")
    val title:String,
    @SerializedName("date")
    val date:String,
    @SerializedName("isLiked")
    val isLiked:Int,
    @SerializedName("likes")
    val likes:Int,
    @SerializedName("read")
    val read:Int,
    @SerializedName("commentcount")
    val commentCount:Int,
    @SerializedName("viewcount")
    val viewCount:Int,
    @SerializedName("tags")
    val tags:List<String>,
    @SerializedName("image")
    val image:String
):Serializable{
    companion object{
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(view: View, image: String?) {
            val imageView = view as AppCompatImageView
            Glide.with(view.context)
                .load(image)
                .error(R.drawable.image_default)
                .into(imageView)
        }
    }
}
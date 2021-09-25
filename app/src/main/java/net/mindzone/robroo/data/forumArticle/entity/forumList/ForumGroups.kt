package net.mindzone.robroo.data.forumArticle.entity.forumList

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName
import net.mindzone.robroo.R
import java.io.Serializable

data class ForumGroups(
    @SerializedName("group_id")
    val groupId:String,
    @SerializedName("title")
    val title:String ="",
    @SerializedName("date")
    val date:String = "",
    @SerializedName("tags")
    val tags:List<String>? = emptyList(),
    @SerializedName("icon")
    val icon:String = "",
):Serializable{
    companion object{
        @JvmStatic
        @BindingAdapter("setImage")
        fun loadImage(view: View, image: String?) {
            val imageView = view as AppCompatImageView
            Glide.with(view.context)
                .load(image)
                .error(R.drawable.ic_share3)
                .into(imageView)
        }
    }
}

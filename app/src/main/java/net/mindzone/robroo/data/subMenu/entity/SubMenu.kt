package net.mindzone.robroo.data.subMenu.entity

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import net.mindzone.robroo.R
import java.io.Serializable

@Entity(tableName = "subMenu")
data class SubMenu(
    @PrimaryKey
    val uniqueid: String = "",
    val titleth: String = "",
    val titleen: String = "",
    val menu : String = "",
    val parentid : String? = "",
    val itemid: Int = 1,
    val action : String = "",
    val actionlinkth : String = "",
    val actionlinken : String = "",
    val icon: String? = null,
    val type: String = "",
    val defaultSelect: Int = 0,
    var selected: Boolean = false,
    val image: Int = R.drawable.ic_2info,
    val image2: Int = R.drawable.ic_info2,
    var position:Int = 0,
    var positionAllSubMenu: Int = 0
): Serializable{
    companion object{
        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(view: View, subMenu: SubMenu) {
            val imageView = view as AppCompatImageView
            Glide.with(view.context)
                .load(subMenu.icon)
                .error(checkMenu(subMenu))
                .into(imageView)
        }

        private fun checkMenu (subMenu: SubMenu): Int {
            when (subMenu.menu){
                "menu2"-> return R.drawable.ic_2info
                "menu3" ->return R.drawable.ic_service
                "menu4" ->return R.drawable.ic_4share
                "menu5" ->return R.drawable.ic_5knowledge
            }
            return R.drawable.ic_2info
        }
    }


}




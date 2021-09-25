package net.mindzone.robroo.ui.viewpager


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import net.mindzone.robroo.R
class ImageAdapter internal constructor(var list: List<String> = emptyList(),val context: Context?) : PagerAdapter() {

    private val mImageIds = intArrayOf(
        R.drawable.default_image,
        R.drawable.default_image,
        R.drawable.default_image,
        R.drawable.default_image,
        R.drawable.default_image,
        R.drawable.default_image,
        R.drawable.default_image,
        R.drawable.default_image
    )

    override fun getCount(): Int {
        return if(list.isEmpty()){
            mImageIds.size
        }else{
            list.size
        }

    }

    override fun isViewFromObject(view: View, objects: Any): Boolean {
        return view === objects
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        if (list.isNotEmpty()){
            Glide.with(context!!).load(list[position]).error(R.drawable.image)
                .into(imageView)
        }else{
            imageView.setImageResource(mImageIds[position])
        }

        container.addView(imageView, 0)
        return imageView
    }
    override fun destroyItem(container: ViewGroup, position: Int, objects: Any) {
        container.removeView(objects as ImageView)
    }

}
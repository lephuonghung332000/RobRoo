package net.mindzone.robroo.ui.main.share.listShare.listDetail.viewPager

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import net.mindzone.robroo.R

class ViewPagerAdapter internal constructor(private val context: Context?, private val images: List<String>) : PagerAdapter() {

    override fun getCount() = images.size

    override fun isViewFromObject(view: View, objects: Any) = view === objects as View

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        if (context != null) {
            Glide.with(context)
                .load(images[position])
                .error(R.drawable.image)
                .into(imageView)
        }
        container.addView(imageView, 0)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, objects: Any) {
        container.removeView(objects as ImageView)
    }
}

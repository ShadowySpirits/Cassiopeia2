package co.bangumi.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions

class ImageUtil {
    companion object {
        fun loadImage(context: Context, view: ImageView, url: String, color: String) {
            val bitmap = Bitmap.createBitmap(2, 3, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(Color.parseColor(color))
            Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .apply(RequestOptions().placeholder(BitmapDrawable(context.resources, bitmap)))
                .transition(withCrossFade())
                .into(view)
        }

        fun loadImage(context: Fragment, view: ImageView, url: String, color: String) {
            val bitmap = Bitmap.createBitmap(2, 3, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(Color.parseColor(color))
            Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .apply(RequestOptions().placeholder(BitmapDrawable(context.resources, bitmap)))
                .transition(withCrossFade())
                .into(view)
        }
    }
}
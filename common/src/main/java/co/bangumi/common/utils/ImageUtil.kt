package co.bangumi.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.fragment.app.Fragment
import co.bangumi.common.model.entity.AbstractBangumi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions

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

fun loadImage(context: Fragment, view: ImageView, bangumi: AbstractBangumi) {
    val bitmap = Bitmap.createBitmap(2, 3, Bitmap.Config.ARGB_8888)
    bitmap.eraseColor(Color.parseColor(bangumi.cover_color))
    Glide.with(context)
        .load(bangumi.cover ?: bangumi.cover_image.url)
        .thumbnail(0.1f)
        .apply(RequestOptions().placeholder(BitmapDrawable(context.resources, bitmap)))
        .transition(withCrossFade())
        .into(view)
}
package co.bangumi.common.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import co.bangumi.common.utils.ImageUtil

@BindingAdapter("bind:imageUrl", "bind:coverColor")
fun setImageUrl(view: ImageView, imageUrl: String, coverColor: String) {
    ImageUtil.loadImage(view.context, view, imageUrl, coverColor)
}
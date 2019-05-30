package co.bangumi.common.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import co.bangumi.common.utils.loadImage

@BindingAdapter("bind:imageUrl", "bind:coverColor")
fun setImageUrl(view: ImageView, imageUrl: String, coverColor: String) {
    loadImage(view.context, view, imageUrl, coverColor)
}
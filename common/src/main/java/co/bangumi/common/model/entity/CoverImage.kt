package co.bangumi.common.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoverImage(
    val value: Int,
    val dominant_color: String,
    val height: Int,
    val width: Int,
    val url: String
) : Parcelable
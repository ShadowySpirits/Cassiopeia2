package co.bangumi.common.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WatchProgress(
    var percentage: Float,
    var last_watch_time: Double
) : Parcelable

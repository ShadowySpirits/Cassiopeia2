package co.bangumi.common.model.entity

import android.os.Parcelable
import android.text.TextUtils
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Bangumi(
    override val id: String,
    val name: String,
    val name_cn: String,
    val image: String,
    val cover: String?,
    val cover_image: CoverImage,
    val cover_color: String,
    val summary: String,
    val air_weekday: Int,
    val air_date: String,
    val eps: Int,
    val type: Int,
    val status: Int,
    var favorite_status: Int,
    val unwatched_count: Int,
    val update_time: Long,
    val bgm_id: Long
) : EntityWithId(), Parcelable {

    val localName: String
        get() {
            return if (Locale.getDefault().displayLanguage == Locale.CHINESE.displayLanguage) {
                if (!TextUtils.isEmpty(name_cn)) name_cn else name
            } else {
                if (TextUtils.isEmpty(name)) name_cn else name
            }
        }

    val subTitle: String
        get() {
            return if (Locale.getDefault().displayLanguage == Locale.CHINESE.displayLanguage) {
                if (!TextUtils.isEmpty(name)) name else name_cn
            } else {
                if (!TextUtils.isEmpty(name)) name_cn else name
            }
        }

    fun isOnAir(): Boolean {
        val airDate = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).parse(air_date)
        val calendar = Calendar.getInstance(Locale.JAPAN)
        val rightNow = calendar.time
        calendar.time = airDate
        calendar.add(Calendar.WEEK_OF_YEAR, eps)
        return rightNow.before(calendar.time)
    }

    override fun areContentsTheSame(other: EntityWithId): Boolean {
        return when (other) {
            !is Bangumi -> false
            else -> unwatched_count == other.unwatched_count && favorite_status == other.favorite_status
        }
    }
}
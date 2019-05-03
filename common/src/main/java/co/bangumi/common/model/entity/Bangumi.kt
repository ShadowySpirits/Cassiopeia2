package co.bangumi.common.model.entity

import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*

data class Bangumi(
    val id: String,
    val name: String,
    val name_cn: String,
    val image: String,
    val cover: String,
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
) {

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Bangumi) {
            return id == other.id
        }
        return false
    }

    fun isOnAir(): Boolean {
        val airDate = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).parse(air_date)
        val calendar = Calendar.getInstance(Locale.JAPAN)
        val rightNow = calendar.time
        calendar.time = airDate
        calendar.add(Calendar.WEEK_OF_YEAR, eps)
        return rightNow.before(calendar.time)
    }

    fun getLocalName(): String {
        return if (Locale.getDefault().displayLanguage == Locale.CHINESE.displayLanguage) {
            if (!TextUtils.isEmpty(name_cn)) name_cn else name
        } else {
            if (TextUtils.isEmpty(name)) name_cn else name
        }
    }
}
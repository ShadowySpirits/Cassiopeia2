package co.bangumi.common.model.entity

import android.os.Parcelable
import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*

abstract class AbstractBangumi : EntityWithId(), Parcelable {
    abstract val name: String
    abstract val name_cn: String
    abstract val image: String
    abstract val cover: String?
    abstract val cover_image: CoverImage
    abstract val cover_color: String
    abstract val summary: String
    abstract val air_weekday: Int
    abstract val air_date: String
    abstract val eps: Int
    abstract val type: Int
    abstract val status: Int
    abstract var favorite_status: Int
    abstract val unwatched_count: Int
    abstract val update_time: Long
    abstract val bgm_id: Long

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
}
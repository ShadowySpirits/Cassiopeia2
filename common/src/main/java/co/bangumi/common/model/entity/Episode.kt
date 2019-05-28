package co.bangumi.common.model.entity

import android.text.TextUtils
import java.util.*

data class Episode(
    val status: Int,
    val episode_no: Int,
    val update_time: Long,
    val name: String,
    val bgm_eps_id: Int,
    val bangumi_id: String,
    val airdate: String,
    val name_cn: String,
    val thumbnail: String,
    val thumbnail_color: String?,
    val delete_mark: Long,
    val create_time: Long,
    val duration: String,
    val id: String,

    val watch_progress: WatchProgress?
) {
    val localName: String
        get() {
            return if (Locale.getDefault().displayLanguage == Locale.CHINESE.displayLanguage) {
                if (TextUtils.isEmpty(name_cn)) name else name_cn
            } else {
                if (TextUtils.isEmpty(name)) name_cn else name
            }
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        if (bangumi_id != bangumi_id)
            return false
        return id == id
    }

    override fun hashCode(): Int {
        var result = bangumi_id.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}

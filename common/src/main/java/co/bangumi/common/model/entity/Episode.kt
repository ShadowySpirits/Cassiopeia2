package co.bangumi.common.model.entity

import android.text.TextUtils
import java.util.*

data class Episode(
    override val id: String,
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
    val watch_progress: WatchProgress?
) : EntityWithId() {
    val localName: String
        get() {
            return if (Locale.getDefault().displayLanguage == Locale.CHINESE.displayLanguage) {
                if (TextUtils.isEmpty(name_cn)) name else name_cn
            } else {
                if (TextUtils.isEmpty(name)) name_cn else name
            }
        }

    override fun areContentsTheSame(other: EntityWithId): Boolean {
        return when (other) {
            !is Episode -> false
            else -> watch_progress == other.watch_progress && thumbnail == other.thumbnail && name == other.name
        }
    }
}

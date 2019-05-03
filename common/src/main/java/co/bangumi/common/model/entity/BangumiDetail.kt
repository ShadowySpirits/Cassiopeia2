package co.bangumi.common.model.entity

import android.text.TextUtils
import java.util.*

data class BangumiDetail(
    val air_date: String,
    val air_weekday: Int,
    val alert_timeout: Int,
    val bangumi_moe: String,
    val bgm_id: Int,
    val cover: String,
    val cover_color: String,
    val cover_image: CoverImage,
    val create_time: Long,
    val created_by_uid: String,
    val episodes: List<Episode>,
    val eps: Int,
    val favorite_status: Int,
    val has_favorited_version: Boolean,
    val id: String,
    val image: String,
    val maintained_by_uid: String,
    val name: String,
    val name_cn: String,
    val status: Int,
    val summary: String,
    val type: Int,
    val update_time: Long
) {
    fun getLocalName(): String {
        return if (Locale.getDefault().displayLanguage == Locale.CHINESE.displayLanguage) {
            if (!TextUtils.isEmpty(name_cn)) name_cn else name
        } else {
            if (TextUtils.isEmpty(name)) name_cn else name
        }
    }
}
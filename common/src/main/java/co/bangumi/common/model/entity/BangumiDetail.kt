package co.bangumi.common.model.entity

import kotlinx.android.parcel.Parcelize

@Parcelize
data class BangumiDetail(
    override val air_date: String,
    override val air_weekday: Int,
    override val bgm_id: Long,
    override val cover: String,
    override val cover_color: String,
    override val cover_image: CoverImage,
    override val eps: Int,
    override var favorite_status: Int,
    override val id: String,
    override val image: String,
    override val name: String,
    override val name_cn: String,
    override val status: Int,
    override val summary: String,
    override val type: Int,
    override val update_time: Long,
    override val unwatched_count: Int,
    val create_time: Long,
    val created_by_uid: String,
    val episodes: List<Episode>,
    val has_favorited_version: Boolean,
    val bangumi_moe: String,
    val maintained_by_uid: String,
    val alert_timeout: Int
) : AbstractBangumi() {

    override fun areContentsTheSame(other: EntityWithId): Boolean {
        return when (other) {
            !is BangumiDetail -> false
            else -> unwatched_count == other.unwatched_count && favorite_status == other.favorite_status
        }
    }
}
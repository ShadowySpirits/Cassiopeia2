package co.bangumi.common.model.entity

import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bangumi(
    override val id: String,
    override val name: String,
    override val name_cn: String,
    override val image: String,
    override val cover: String?,
    override val cover_image: CoverImage,
    override val cover_color: String,
    override val summary: String,
    override val air_weekday: Int,
    override val air_date: String,
    override val eps: Int,
    override val type: Int,
    override val status: Int,
    override var favorite_status: Int,
    override val unwatched_count: Int,
    override val update_time: Long,
    override val bgm_id: Long
) : AbstractBangumi() {

    override fun areContentsTheSame(other: EntityWithId): Boolean {
        return when (other) {
            !is Bangumi -> false
            else -> unwatched_count == other.unwatched_count && favorite_status == other.favorite_status
        }
    }
}
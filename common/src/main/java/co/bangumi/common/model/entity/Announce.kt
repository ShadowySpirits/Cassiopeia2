package co.bangumi.common.model.entity

data class Announce(
    val id: String,
    val position: Int,

    val start_time: Long,
    val end_time: Long,
    val sort_order: Long,
    val content: String,
    val image_url: String,

    val bangumi: Bangumi
)
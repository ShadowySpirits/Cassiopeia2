package co.bangumi.common.model.entity

data class CoverImage(
    val value: Int,
    val dominant_color: String,
    val height: Int,
    val width: Int,
    val url: String
)
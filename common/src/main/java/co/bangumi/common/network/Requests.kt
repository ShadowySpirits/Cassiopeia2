package co.bangumi.common.network

data class LoginRequest(val name: String, val password: String, val remmember: Boolean)

data class FavoriteChangeRequest(val status: Int)

data class HistoryChangeItem(
    val bangumi_id: String,
    val episode_id: String,
    val last_watch_time: Long,
    val last_watch_position: Long,
    val percentage: Float,
    val is_finished: Boolean
)

data class HistoryChangeRequest(val records: List<HistoryChangeItem>)
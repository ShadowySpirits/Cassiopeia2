package co.bangumi.common.network

import co.bangumi.common.model.entity.*
import co.bangumi.framework.network.MessageResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /**
     * Users
     */

    @POST("/api/user/login")
    suspend fun login(@Body body: LoginRequest): Response<MessageResponse>

    @POST("api/user/logout")
    fun logout(): Deferred<MessageResponse>

    @GET("/api/user/info")
    fun getUserInfo(): Deferred<DataResponse<UserInfo>>


    /**
     * Bangumi info
     */

    @GET("/api/home/my_bangumi")
    fun getMyBangumi(
        @Query("page") page: Int?,
        @Query("count") count: Int?,
        @Query("status") status: Int?
    ): Deferred<ListResponse<Bangumi>>

    @GET("/api/home/announce")
    fun getAnnounceBangumi(): Deferred<ListResponse<Announce>>

    @GET("/api/home/on_air")
    fun getOnAir(@Query("type") type: Int?): Deferred<ListResponse<Bangumi>>

    @GET("/api/home/bangumi")
    fun getSearchBangumi(
        @Query("page") page: Int?,
        @Query("count") count: Int?,
        @Query("sort_field") sortField: String?,
        @Query("sort_order") sortOrder: String?,
        @Query("name") name: String?,
        @Query("type") type: Int?
    ): Deferred<ListResponse<Bangumi>>

    @GET("/api/home/bangumi/{id}")
    fun getBangumiDetail(@Path("id") id: String): Deferred<DataResponse<BangumiDetail>>

    @GET("/api/home/episode/{id}")
    fun getEpisodeDetail(@Path("id") id: String): Deferred<EpisodeDetail>

    /**
     * Favorite and history
     */

    @POST("/api/watch/favorite/bangumi/{bangumi_id}")
    fun uploadFavoriteStatus(@Path("bangumi_id") bangumiId: String, @Body body: FavoriteChangeRequest): Deferred<MessageResponse>

    @POST("/api/watch/history/synchronize")
    fun uploadWatchHistory(@Body body: HistoryChangeRequest): Deferred<MessageResponse>
}
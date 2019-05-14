package co.bangumi.common.network

import co.bangumi.common.annotation.SubType
import co.bangumi.common.annotation.WatchStatus
import co.bangumi.common.model.entity.*
import co.bangumi.framework.network.MessageResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /**
     * Users
     */

    @POST("/api/user/login")
    suspend fun login(@Body body: LoginRequest): Response<MessageResponse>

    @POST("api/user/logout")
    suspend fun logout(): MessageResponse

    @GET("/api/user/info")
    suspend fun getUserInfo(): DataResponse<UserInfo>


    /**
     * Bangumi info
     */

    @GET("/api/home/my_bangumi")
    suspend fun getMyBangumi(
        @Query("page") @WatchStatus page: Int?,
        @Query("count") count: Int?,
        @Query("status") status: Int?
    ): ListResponse<Bangumi>

    @GET("/api/home/announce")
    suspend fun getAnnounceBangumi(): ListResponse<Announce>

    @GET("/api/home/on_air")
    suspend fun getOnAir(@Query("type") @SubType type: Int?): ListResponse<Bangumi>

    @GET("/api/home/bangumi")
    suspend fun getSearchBangumi(
        @Query("page") page: Int?,
        @Query("count") count: Int?,
        @Query("sort_field") sortField: String?,
        @Query("sort_order") sortOrder: String?,
        @Query("name") name: String?,
        @Query("type") type: Int?
    ): ListResponse<Bangumi>

    @GET("/api/home/bangumi/{id}")
    suspend fun getBangumiDetail(@Path("id") id: String): DataResponse<BangumiDetail>

    @GET("/api/home/episode/{id}")
    suspend fun getEpisodeDetail(@Path("id") id: String): EpisodeDetail

    /**
     * Favorite and history
     */

    @POST("/api/watch/favorite/bangumi/{bangumi_id}")
    suspend fun uploadFavoriteStatus(@Path("bangumi_id") bangumiId: String, @Body body: FavoriteChangeRequest): MessageResponse

    @POST("/api/watch/history/synchronize")
    suspend fun uploadWatchHistory(@Body body: HistoryChangeRequest): MessageResponse
}
package co.bangumi.cassiopeia.repository

import co.bangumi.common.annotation.ALL
import co.bangumi.common.annotation.WATCHING
import co.bangumi.common.model.entity.Announce
import co.bangumi.common.model.entity.Bangumi
import co.bangumi.common.network.ApiService
import co.bangumi.common.network.ListResponse
import co.bangumi.common.network.LoginRequest
import co.bangumi.framework.network.MessageResponse
import retrofit2.Response

class DataRepository(private val remote: ApiService) {
    suspend fun login(name: String, password: String): Response<MessageResponse> {
        return remote.login(LoginRequest(name, password, true))
    }

    suspend fun getAnnounceBangumi(): ListResponse<Announce> {
        return remote.getAnnounceBangumi()
    }

    suspend fun getMyBangumi(): ListResponse<Bangumi> {
        return remote.getMyBangumi(1, -1, WATCHING)
    }

    suspend fun getOnAir(): ListResponse<Bangumi> {
        return remote.getOnAir(ALL)
    }
}
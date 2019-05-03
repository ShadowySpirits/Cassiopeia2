package co.bangumi.cassiopeia.repository

import co.bangumi.common.network.ApiService
import co.bangumi.common.network.LoginRequest
import co.bangumi.framework.network.MessageResponse
import retrofit2.Response

class DataRepository(private val remote: ApiService) {
    suspend fun login(name: String, password: String): Response<MessageResponse> {
        return remote.login(LoginRequest(name, password, true))
    }
}
package co.bangumi.common.network

import co.bangumi.framework.network.ApiException
import co.bangumi.framework.network.RequestHandler
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class NetHandler : RequestHandler {

    override fun onBeforeRequest(request: Request, chain: Interceptor.Chain): Request {
        return request
    }

    @Throws(IOException::class)
    override fun onAfterRequest(response: Response, chain: Interceptor.Chain): Response {
        when (response.code()) {
            401 -> throw ApiException("Unauthorized")
            403 -> throw ApiException("Forbidden")
            404 -> throw ApiException("Not Found")
            500 -> throw ApiException("Internal Server Error")
            503 -> throw ApiException("Service Unavailable")
        }
        return response
    }
}
package co.bangumi.framework.network

import co.bangumi.framework.annotation.AllOpen
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


@AllOpen
class NetInterceptor(private val handler: RequestHandler) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = handler.onBeforeRequest(request, chain)

        val response = chain.proceed(request)
        return handler.onAfterRequest(response, chain)
    }
}
package co.bangumi.common.network

import android.content.Context
import co.bangumi.common.BASE_URL
import co.bangumi.framework.network.NetInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var apiService: ApiService? = null
    private lateinit var retrofit: Retrofit
    private lateinit var cookieJar: PersistentCookieJar

    fun init(context: Context) {
        apiService ?: synchronized(ApiClient::javaClass) {
            apiService =
                create(context, BASE_URL)
        }
    }

    fun clearCookie() {
        cookieJar.clear()
    }

    fun getApiService(): ApiService {
        return apiService ?: throw IllegalStateException("ApiClient Not being initialized")
    }

    fun getApiService(context: Context): ApiService {
        return apiService ?: run {
            init(context)
            apiService!!
        }
    }

    private fun create(context: Context, server: String): ApiService {
        cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))

        val okHttp = OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .cookieJar(cookieJar)
            .addInterceptor(NetInterceptor(NetHandler()))
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(server)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}

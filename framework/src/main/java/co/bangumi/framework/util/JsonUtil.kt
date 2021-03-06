package co.bangumi.framework.util

import android.util.Log
import co.bangumi.framework.BuildConfig
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import retrofit2.Response

private val gson by lazy { Gson() }

fun toJson(src: Any): String {
    return gson.toJson(src)
}

fun <T> fromJson(src: String, classOfT: Class<T>): T? {
    try {
        return gson.fromJson(src, classOfT)
    } catch (e: JsonParseException) {
        if (BuildConfig.DEBUG) Log.w("JsonUtil", "JsonParseException", e)
    }
    return null
}

fun toJson(src: List<*>): String {
    return gson.toJson(src)
}

fun <T> fromJson(src: String, typeToken: TypeToken<*>): T {
    return gson.fromJson<T>(src, typeToken.type)
}

fun <T> convertErrorBody(response: Response<out Any>, classOfT: Class<T>): T? {
    return fromJson(response.errorBody()?.string()!!, classOfT)
}

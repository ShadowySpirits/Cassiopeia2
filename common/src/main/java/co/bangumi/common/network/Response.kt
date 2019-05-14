package co.bangumi.common.network

import co.bangumi.framework.network.ApiException
import co.bangumi.framework.network.MessageResponse

class DataResponse<T> : MessageResponse() {
    private var data: T? = null

    fun getData(): T {
        data?.let {
            return it
        }
        throw ApiException("Empty Data")
    }

    override fun toString(): String {
        return super.toString() + " data:" + data?.toString()
    }
}

class ListResponse<T> : MessageResponse() {
    private val data: List<T>? = null
    val status: Int? = null
    val total: Int? = null
    val count: Int? = null

    fun getData(): List<T> {
        return data ?: emptyList()
    }

    override fun toString(): String {
        return super.toString() + " list:" + getData().toString()
    }
}
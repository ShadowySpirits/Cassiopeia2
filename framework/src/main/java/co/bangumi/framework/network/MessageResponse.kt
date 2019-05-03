package co.bangumi.framework.network

import co.bangumi.framework.annotation.AllOpen

@AllOpen
class MessageResponse {
    private var msg: String? = null
    private var message: String? = null

    fun message(): String? = msg ?: message

    override fun toString(): String {
        return "message:" + message()
    }
}
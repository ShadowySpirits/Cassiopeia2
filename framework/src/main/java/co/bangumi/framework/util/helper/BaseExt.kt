package co.bangumi.framework.util.helper

import android.app.Activity
import android.widget.Toast
import co.bangumi.framework.BuildConfig
import co.bangumi.framework.annotation.*
import es.dmoral.toasty.Toasty
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Activity.toast(msg: CharSequence, @ToastType type: Int = NORMAL, duration: Int = Toast.LENGTH_SHORT) {
    when (type) {
        WARNING -> Toasty.warning(this, msg, duration, true).show()
        ERROR -> Toasty.error(this, msg, duration, true).show()
        NORMAL -> Toasty.info(this, msg, duration, false).show()
        SUCCESS -> Toasty.success(this, msg, duration, true).show()
    }
}

fun Activity.dispatchFailure(error: Throwable?) {
    error?.let {
        if (BuildConfig.DEBUG) {
            it.printStackTrace()
        }
        when (it) {
            is SocketTimeoutException -> toastError("网络连接超时")
            is UnknownHostException -> toastError("网络异常")
            else -> toastError("unknown error")
        }
    }
}

fun Activity.toastSuccess(msg: String?) {
    msg?.let { toast(it, SUCCESS) }
}

fun Activity.toastWarning(msg: String?) {
    msg?.let { toast(it, WARNING) }
}

fun Activity.toastError(msg: String?) {
    msg?.let { toast(it, ERROR) }
}

fun Activity.toastFailure(error: Throwable?) {
    dispatchFailure(error)
}

fun <T : Any> Activity.argument(key: String) =
    lazy { intent.extras[key] as? T ?: error("Intent Argument $key is missing") }


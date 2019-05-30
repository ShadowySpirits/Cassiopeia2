package co.bangumi.framework.util.extension

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
        NORMAL -> Toasty.normal(this, msg, duration).show()
        INFO -> Toasty.info(this, msg, duration, true).show()
        SUCCESS -> Toasty.success(this, msg, duration, true).show()
    }
}

// TODO 多语言支持
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

fun Activity.toastWarning(msg: String?) {
    msg?.let { toast(it, WARNING) }
}

fun Activity.toastError(msg: String?) {
    msg?.let { toast(it, ERROR) }
}

fun Activity.toastNormal(msg: String?) {
    msg?.let { toast(it, NORMAL) }
}

fun Activity.toastInfo(msg: String?) {
    msg?.let { toast(it, INFO) }
}

fun Activity.toastSuccess(msg: String?) {
    msg?.let { toast(it, SUCCESS) }
}

fun Activity.toastFailure(error: Throwable?) {
    dispatchFailure(error)
}

// Notice: using this function may cause IllegalStateException or ClassCastException
@Suppress("UNCHECKED_CAST")
fun <T> Activity.argument(key: String) =
    intent.extras?.let {
        it[key] as T
    }


package co.bangumi.framework.util.helper

import co.bangumi.framework.base.BaseActivity
import kotlinx.coroutines.*

inline fun <T> BaseActivity<*>.requestAsync(
    crossinline block: suspend () -> T,
    crossinline onSuccess: (response: T) -> Unit,
    crossinline onFailure: (e: Throwable) -> Unit
): Deferred<Unit> {
    return async(Dispatchers.IO, CoroutineStart.LAZY) {
        try {
            block().run {
                withContext(Dispatchers.Main) {
                    onSuccess(this@run)
                }
            }
        } catch (e: Throwable) {
            withContext(Dispatchers.Main) {
                onFailure(e)
            }
        }
    }
}

inline fun <T> BaseActivity<*>.requestAsync(
    crossinline block: suspend () -> T,
    crossinline onSuccess: (response: T) -> Unit
): Deferred<Unit> {
    return async(Dispatchers.IO, CoroutineStart.LAZY) {
        try {
            block().run {
                withContext(Dispatchers.Main) {
                    onSuccess(this@run)
                }
            }
        } catch (e: Throwable) {
            withContext(Dispatchers.Main) {
                this@requestAsync.toastFailure(e)
            }
        }
    }
}

@Suppress("DeferredResultUnused")
inline fun <T> BaseActivity<*>.requestAsync(
    vararg blocks: suspend () -> T,
    crossinline onComplete: (response: T) -> Unit
): Deferred<Unit> {
    return async(Dispatchers.IO, CoroutineStart.LAZY) {
        val supervisor = SupervisorJob()
        with(CoroutineScope(coroutineContext + supervisor)) {
            blocks.forEach {
                async {
                    it().run {
                        withContext(Dispatchers.Main) {
                            onComplete(this@run)
                        }
                    }
                }
            }
        }
    }
}
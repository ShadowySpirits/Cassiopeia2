package co.bangumi.framework.util.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import co.bangumi.framework.base.BaseActivity
import co.bangumi.framework.base.BaseFragment
import kotlinx.coroutines.*

inline fun <T> BaseActivity<*>.request(
    crossinline block: suspend () -> Deferred<T>,
    crossinline onSuccess: (response: T) -> Unit
) {
    lifecycleScope.launch {
        try {
            onSuccess(block().await())
        } catch (e: Throwable) {
            dispatchFailure(e)
        }
    }
}

inline fun <T> BaseFragment<*>.request(
    crossinline block: suspend () -> Deferred<T>,
    crossinline onSuccess: (response: T) -> Unit
) {
    lifecycleScope.launch {
        try {
            onSuccess(block().await())
        } catch (e: Throwable) {
            activity?.dispatchFailure(e)
        }
    }
}

inline fun <T> LifecycleOwner.request(
    crossinline block: suspend () -> Deferred<T>,
    crossinline onSuccess: (response: T) -> Unit,
    crossinline onFailure: (e: Throwable) -> Unit
) {
    lifecycleScope.launch {
        try {
            onSuccess(block().await())
        } catch (e: Throwable) {
            onFailure(e)
        }
    }
}

inline fun <T, R> ViewModel.requestAsync(
    crossinline block: suspend () -> T,
    crossinline onSuccess: (response: T) -> R
): Deferred<R> {
    return viewModelScope.async(Dispatchers.IO, CoroutineStart.LAZY) {
        onSuccess(block())
    }
}

inline fun <T> ViewModel.requestAsync(
    crossinline block: suspend () -> T
): Deferred<T> {
    return viewModelScope.async(Dispatchers.IO, CoroutineStart.LAZY) {
        block()
    }
}

@Suppress("DeferredResultUnused")
inline fun <T> ViewModel.requestsAsync(
    vararg blocks: suspend () -> T,
    crossinline onComplete: (response: T) -> Unit
): Deferred<Unit> {
    return viewModelScope.async(Dispatchers.IO, CoroutineStart.LAZY) {
        val supervisor = SupervisorJob()
        with(CoroutineScope(coroutineContext + supervisor)) {
            blocks.forEach {
                async {
                    onComplete(it())
                }
            }
        }
    }
}
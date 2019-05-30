package co.bangumi.framework.util.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import co.bangumi.framework.base.BaseActivity
import co.bangumi.framework.base.BaseFragment
import kotlinx.coroutines.*

inline fun <T> BaseActivity<*>.loadDataAsync(
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

inline fun <T> BaseActivity<*>.loadDataBundleAsync(
    vararg blocks: suspend () -> Deferred<T>,
    crossinline onComplete: () -> Unit
) {
    lifecycleScope.launch {
        try {
            blocks.map { it() }.awaitAll()
        } catch (e: Throwable) {
            dispatchFailure(e)
        } finally {
            onComplete()
        }
    }
}

inline fun <T> BaseFragment<*>.loadDataAsync(
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

inline fun <T> BaseFragment<*>.loadDataBundleAsync(
    vararg blocks: suspend () -> Deferred<T>,
    crossinline onComplete: () -> Unit
) {
    lifecycleScope.launch {
        try {
            blocks.map { it() }.awaitAll()
        } catch (e: Throwable) {
            activity?.dispatchFailure(e)
        } finally {
            onComplete()
        }
    }
}

inline fun <T> LifecycleOwner.requestAsync(
    crossinline block: suspend () -> Deferred<T>,
    crossinline onSuccess: (response: T) -> Unit = {},
    crossinline onError: (e: Throwable) -> Unit = {},
    crossinline onComplete: () -> Unit = {}
) {
    lifecycleScope.launch {
        try {
            onSuccess(block().await())
        } catch (e: Throwable) {
            onError(e)
        } finally {
            onComplete()
        }
    }
}

inline fun <T> LifecycleOwner.requestBundleAsync(
    vararg blocks: suspend () -> Deferred<T>,
    crossinline onAllSuccess: (response: List<T>) -> Unit = {},
    crossinline onError: (e: Throwable) -> Unit = {},
    crossinline onComplete: () -> Unit = {}
) {
    lifecycleScope.launch {
        try {
            onAllSuccess(blocks.map { it() }.awaitAll())
        } catch (e: Throwable) {
            onError(e)
        } finally {
            onComplete()
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
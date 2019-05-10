package co.bangumi.framework.base

import androidx.lifecycle.ViewModel
import co.bangumi.framework.annotation.AllOpen
import kotlinx.coroutines.Deferred
import java.util.*

@AllOpen
class BaseViewModel : ViewModel() {

    private val runningMap by lazy { HashMap<String, Deferred<*>>() }

    @Suppress("DeferredResultUnused", "UNCHECKED_CAST")
    protected fun <T> Deferred<T>.startSingleInstance(taskId: String, displace: Boolean = true): Deferred<T> {
        this.invokeOnCompletion { runningMap.remove(taskId) }
        if (runningMap.containsKey(taskId)) {
            if (!displace) {
                return runningMap[taskId] as Deferred<T>
            }
            runningMap[taskId]?.cancel()
        }
        runningMap[taskId] = this
        this.start()
        return this
    }

    protected fun getUUID() = UUID.randomUUID().toString()
}
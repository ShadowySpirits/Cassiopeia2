package co.bangumi.framework.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.bangumi.framework.annotation.AllOpen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import java.util.*

@AllOpen
abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity(), BasePresenter,
    CoroutineScope by CoroutineScope(Dispatchers.IO) {

    protected val MIN_CLICK_DELAY = 600L
    private var previousClickTime: Long = 0
    private val runningMap by lazy { IdentityHashMap<String, Deferred<*>>() }
    protected val mBinding: VB by lazy { DataBindingUtil.setContentView<VB>(this, getLayoutId()) }
    protected var autoRefresh = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.executePendingBindings()
        mBinding.lifecycleOwner = this

        initView()
        if (autoRefresh) {
            loadData(true)
        }
    }

    abstract fun initView()

    abstract fun getLayoutId(): Int

    override fun onClick(view: View) {
        val currentClickTime = System.currentTimeMillis()

        if (previousClickTime == 0L || currentClickTime - previousClickTime >= MIN_CLICK_DELAY) {
            previousClickTime = currentClickTime
            this.onDebouncedClick(view)
        }
    }

    @Suppress("DeferredResultUnused")
    protected fun <T> Deferred<T>.startSingleInstance(taskId: String, displace: Boolean = true) {
        this.invokeOnCompletion { runningMap.remove(taskId) }
        if (runningMap.containsKey(taskId)) {
            if (!displace) {
                return
            }
            runningMap[taskId]?.cancel()
        }
        runningMap[taskId] = this
        this.start()
    }

    protected fun getUUID() = UUID.randomUUID().toString()

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun initBackToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)

        val bar = supportActionBar
        if (bar != null) {
            bar.title = null
            bar.setDisplayHomeAsUpEnabled(true)
            bar.setDisplayShowHomeEnabled(true)
            bar.setDisplayShowTitleEnabled(true)
            bar.setHomeButtonEnabled(true)
        }
    }
}
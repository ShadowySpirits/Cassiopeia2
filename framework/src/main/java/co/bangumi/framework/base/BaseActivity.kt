package co.bangumi.framework.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.bangumi.framework.annotation.AllOpen

@AllOpen
abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity(), BasePresenter {

    protected val MIN_CLICK_DELAY = 600L
    private var previousClickTime: Long = 0
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

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> onBackPressed()
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    fun initBackToolbar(toolbar: Toolbar) {
//        setSupportActionBar(toolbar)
//
//        val bar = supportActionBar
//        if (bar != null) {
//            bar.title = null
//            bar.setDisplayHomeAsUpEnabled(true)
//            bar.setDisplayShowHomeEnabled(true)
//            bar.setDisplayShowTitleEnabled(true)
//            bar.setHomeButtonEnabled(true)
//        }
//    }
}
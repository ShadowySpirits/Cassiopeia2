package co.bangumi.framework.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.bangumi.framework.annotation.AllOpen

@AllOpen
abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {

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

    abstract fun loadData(isRefresh: Boolean)

    abstract fun initView()

    abstract fun getLayoutId(): Int


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
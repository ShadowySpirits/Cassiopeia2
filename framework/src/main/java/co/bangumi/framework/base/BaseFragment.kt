package co.bangumi.framework.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import co.bangumi.framework.annotation.AllOpen
import co.bangumi.framework.annotation.SUCCESS
import co.bangumi.framework.util.helper.dispatchFailure
import co.bangumi.framework.util.helper.toast

@AllOpen
abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    protected val mBinding: VB by lazy { DataBindingUtil.inflate<VB>(layoutInflater, getLayoutId(), null, false) }

    protected var lazyLoad = false

    protected var visible = false

    protected var isPrepared: Boolean = false
    protected var hasLoadOnce: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgs(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
        initView()
        if (lazyLoad) {
            lazyLoad()
        } else {
            loadData(true)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding.executePendingBindings()
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    /**
     * 是否可见，延迟加载
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            visible = true
            onVisible()
        } else {
            visible = false
            onInvisible()
        }
    }

    protected fun onInvisible() {

    }

    protected fun onVisible() {
        lazyLoad()
    }


    fun lazyLoad() {}

    fun initArgs(savedInstanceState: Bundle?) {

    }

    abstract fun initView()
    abstract fun loadData(isRefresh: Boolean)

    abstract fun getLayoutId(): Int

    fun toastSuccess(msg: String?) {
        msg?.let { activity?.toast(it, SUCCESS) }
    }

    fun toastFailure(error: Throwable) {
        activity?.dispatchFailure(error)
    }
}
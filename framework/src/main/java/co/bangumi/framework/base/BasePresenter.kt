package co.bangumi.framework.base

import android.view.View

interface BasePresenter : View.OnClickListener {

    fun loadData(isRefresh: Boolean)

    // TODO 添加防抖 https://www.jianshu.com/p/28751130c038
    override fun onClick(v: View?)
}
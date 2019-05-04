package co.bangumi.framework.base

import android.view.View

interface BasePresenter : View.OnClickListener {

    fun loadData(isRefresh: Boolean)

    fun onDebouncedClick(view: View)

    override fun onClick(view: View)
}
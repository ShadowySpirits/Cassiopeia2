package co.bangumi.framework.base

import android.view.View

interface BasePresenter : View.OnClickListener {

    fun onDebouncedClick(view: View)

    override fun onClick(view: View)

    fun onClick(view: View, onDebouncedClick: (view: View) -> Unit)
}
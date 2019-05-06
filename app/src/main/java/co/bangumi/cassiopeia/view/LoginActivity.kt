package co.bangumi.cassiopeia.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import co.bangumi.cassiopeia.R
import co.bangumi.cassiopeia.databinding.ActivityLoginBinding
import co.bangumi.cassiopeia.viewmodel.LoginViewModel
import co.bangumi.common.utils.ConfigureUtil
import co.bangumi.framework.base.BaseActivity
import co.bangumi.framework.network.MessageResponse
import co.bangumi.framework.util.JsonUtil
import co.bangumi.framework.util.helper.*
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val mViewModel: LoginViewModel by viewModel()
    private val taskId: String = getUUID()

    override fun loadData(isRefresh: Boolean) {}

    override fun initView() {
        mBinding.presenter = this
        mBinding.vm = mViewModel
        mBinding.layout.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > 200)) {
                mBinding.pw.clearFocus()
                mBinding.user.clearFocus()
            }
        }
        mBinding.pw.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                mBinding.fab.callOnClick()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun onDebouncedClick(view: View) {
        if (mBinding.user.check("账号不能为空") and mBinding.pw.check("密码不能为空")) {
            toastInfo(getString(R.string.connecting))
            requestAsync(mViewModel::login) {
                if (it.isSuccessful) {
                    toastSuccess(it.body()?.message())
                    ConfigureUtil.setUsername(mBinding.user.text.toString())
                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.METHOD, "origin")
                    FirebaseAnalytics.getInstance(this)
                        .logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
                } else {
                    toastError(JsonUtil.convertErrorBody(it, MessageResponse::class.java)?.message())
                }
            }.startSingleInstance(taskId)
        }
    }
}

package co.bangumi.cassiopeia.view

import android.view.View
import co.bangumi.cassiopeia.R
import co.bangumi.cassiopeia.databinding.ActivityLoginBinding
import co.bangumi.cassiopeia.viewmodel.LoginViewModel
import co.bangumi.framework.base.BaseActivity
import co.bangumi.framework.network.MessageResponse
import co.bangumi.framework.util.JsonUtil
import co.bangumi.framework.util.helper.check
import co.bangumi.framework.util.helper.requestAsync
import co.bangumi.framework.util.helper.toastError
import co.bangumi.framework.util.helper.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val mViewModel: LoginViewModel by viewModel()
    private val taskId: String = getUUID()

    override fun loadData(isRefresh: Boolean) {}

    override fun initView() {
        mBinding.presenter = this
        mBinding.vm = mViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun onDebouncedClick(view: View) {
        if (mBinding.user.check("账号不能为空") and mBinding.pw.check("密码不能为空")) {
            requestAsync(mViewModel::login) {
                if (it.isSuccessful) {
                    toastSuccess(it.body()?.message())
                } else {
                    toastError(JsonUtil.convertErrorBody(it, MessageResponse::class.java)?.message())
                }
            }.startSingleInstance(taskId)
        }
    }
}

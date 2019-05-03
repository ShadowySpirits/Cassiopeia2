package co.bangumi.cassiopeia.view

import android.view.View
import co.bangumi.cassiopeia.R
import co.bangumi.cassiopeia.databinding.ActivityLoginBinding
import co.bangumi.cassiopeia.viewmodel.LoginViewModel
import co.bangumi.framework.base.BaseActivity
import co.bangumi.framework.base.BasePresenter
import co.bangumi.framework.network.MessageResponse
import co.bangumi.framework.util.JsonUtil
import co.bangumi.framework.util.helper.asyncRequest
import co.bangumi.framework.util.helper.check
import co.bangumi.framework.util.helper.toastError
import co.bangumi.framework.util.helper.toastSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>(), BasePresenter {

    private val mViewModel: LoginViewModel by viewModel()

    override fun loadData(isRefresh: Boolean) {}

    override fun initView() {
        mBinding.presenter = this
        mBinding.vm = mViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun onClick(v: View?) {
        // TODO EditText 光标不消失
        if (mBinding.user.check("账号不能为空") and mBinding.pw.check("密码不能为空")) {
            asyncRequest({ mViewModel.login() }, { response ->
                if (response.isSuccessful) {
                    toastSuccess(response.body()?.message())
                } else {
                    toastError(JsonUtil.convertErrorBody(response, MessageResponse::class.java)?.message())
                }
            })
        }
    }
}

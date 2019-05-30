package co.bangumi.cassiopeia.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.bangumi.cassiopeia.INTENT_KEY_BGM_DETAIL
import co.bangumi.cassiopeia.R
import co.bangumi.cassiopeia.viewmodel.DetailViewModel
import co.bangumi.common.utils.isConfigured
import co.bangumi.framework.util.extension.requestAsync
import co.bangumi.framework.util.extension.toastError
import co.bangumi.framework.util.getVersionCode
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartUpActivity : AppCompatActivity() {

    private val mViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isConfigured = isConfigured()
        val intent = intent
        if (isConfigured && intent.action == Intent.ACTION_VIEW) {
            var url = intent.dataString
            FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this) { pendingDynamicLinkData ->
                    if (pendingDynamicLinkData != null) {
                        url = pendingDynamicLinkData.link.toString()
                        if (pendingDynamicLinkData.minimumAppVersion > getVersionCode(this)) {
                            startActivity(pendingDynamicLinkData.getUpdateAppIntent(this))
                        }
                    }

                    requestAsync(
                        { mViewModel.getBangumiDetailAsync(url!!.substring(url!!.lastIndexOf("/") + 1)) },
                        { startActivity(intentFor<HomeActivity>(INTENT_KEY_BGM_DETAIL to it)) },
                        { toastError(getString(R.string.empty)) },
                        ::finish
                    )
                }.addOnFailureListener { finish() }
        } else {
            if (isConfigured) {
                startActivity<HomeActivity>()
            } else {
                startActivity<LoginActivity>()
            }
            finish()
        }
    }
}
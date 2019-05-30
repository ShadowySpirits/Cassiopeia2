package co.bangumi.cassiopeia.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.bangumi.cassiopeia.R
import co.bangumi.cassiopeia.viewmodel.DetailViewModel
import co.bangumi.common.utils.isConfigured
import co.bangumi.framework.util.extension.requestAsync
import co.bangumi.framework.util.extension.toastError
import co.bangumi.framework.util.getVersionCode
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import org.jetbrains.anko.startActivity

class StartUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isConfigured = isConfigured()
        val intent = intent
        if (isConfigured && intent.action == Intent.ACTION_VIEW) {
            // TODO open DetailActivity here
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
                    getString(R.string.empty)
                }
        } else {
            if (isConfigured) {
                startActivity<HomeActivity>()
            } else {
                startActivity<LoginActivity>()
            }
        }
        finish()
    }
}
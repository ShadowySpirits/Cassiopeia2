package co.bangumi.cassiopeia.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.bangumi.cassiopeia.R
import co.bangumi.common.utils.ConfigureUtil
import co.bangumi.framework.util.PackageUtil
import co.bangumi.framework.util.helper.toastNormal
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import org.jetbrains.anko.startActivity

class StartUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isConfigured = ConfigureUtil.configured()
        val intent = intent
        if (isConfigured && intent.action == Intent.ACTION_VIEW) {
            var url = intent.dataString
            FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this) { pendingDynamicLinkData ->
                    if (pendingDynamicLinkData != null) {
                        url = pendingDynamicLinkData.link.toString()
                        if (pendingDynamicLinkData.minimumAppVersion > PackageUtil.getVersionCode(this)) {
                            startActivity(pendingDynamicLinkData.getUpdateAppIntent(this))
                        }
                    }
                    getString(R.string.empty)
                }
        } else {
            if (isConfigured) {
                toastNormal("isLogin")
            } else {
                startActivity<LoginActivity>()
            }
        }
        finish()
    }
}
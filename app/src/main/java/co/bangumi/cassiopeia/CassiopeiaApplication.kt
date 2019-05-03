package co.bangumi.cassiopeia

import co.bangumi.cassiopeia.di.appModule
import co.bangumi.common.network.ApiClient
import co.bangumi.framework.base.BaseApplication
import co.bangumi.framework.util.PreferencesUtil
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CassiopeiaApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        PreferencesUtil.init(this)
        ApiClient.init(this)
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            androidContext(this@CassiopeiaApplication)
            modules(appModule)
        }
    }
}

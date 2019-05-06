package co.bangumi.framework.base

import android.app.Application
import co.bangumi.framework.annotation.AllOpen
import es.dmoral.toasty.Toasty

@AllOpen
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Toasty.Config.getInstance()
            .allowQueue(true)
            .apply()
    }
}
package co.bangumi.cassiopeia.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import co.bangumi.cassiopeia.R
import co.bangumi.common.network.ApiClient
import co.bangumi.framework.util.PreferenceUtil
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.appindexing.FirebaseAppIndex
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor

class HomeActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private val mFirebaseAnalytics: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(this) }

    // TODO 加载用户名
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, drawer_layout)
        nav_view.setupWithNavController(navController)
        nav_view.setNavigationItemSelectedListener {
            if (it.itemId == R.id.nav_logout) {
                logout()
                return@setNavigationItemSelectedListener false
            }
            it.onNavDestinationSelected(navController)
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.home, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//    }

    private fun logout() {
        PreferenceUtil.getInstance().clear()
        ApiClient.clearCookie()
        FirebaseAppIndex.getInstance().removeAll()
        startActivity(intentFor<LoginActivity>().clearTop())
        finishAffinity()
    }
}

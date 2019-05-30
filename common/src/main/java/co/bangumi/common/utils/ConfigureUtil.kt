package co.bangumi.common.utils

import android.text.TextUtils
import co.bangumi.framework.util.PreferenceUtil

const val KEY_USERNAME = "CassiopeiaConfigure.username"

fun isConfigured(): Boolean {
    return !(TextUtils.isEmpty(getUsername()))
}

fun setUsername(server: String) {
    PreferenceUtil.getInstance().putString(KEY_USERNAME, server)
}

fun getUsername(): String {
    return PreferenceUtil.getInstance().getString(KEY_USERNAME, "")
}
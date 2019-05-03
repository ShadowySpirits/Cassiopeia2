package co.bangumi.framework.util.helper

import android.text.Editable
import android.widget.EditText

fun EditText.check(msg: String): Boolean {
    this.apply {
        if (text.isNullOrBlank()) {
            error = msg
            return false
        }
    }
    return true
}

fun EditText.check(msg: String, check: (text: Editable?) -> Boolean): Boolean {
    this.apply {
        if (check(text)) {
            error = msg
            return false
        }
    }
    return true
}
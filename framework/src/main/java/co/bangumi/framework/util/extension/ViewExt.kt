package co.bangumi.framework.util.extension

import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import kotlin.math.roundToInt

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

fun TextView.adaptWidth() {
    val params = layoutParams
    params.width = paint.measureText(text.toString()).roundToInt() + paddingLeft + paddingRight
    layoutParams = params
}
package co.bangumi.common.utils.helper

import android.widget.TextView
import kotlin.math.roundToInt

fun TextView.adaptWidth() {
    val params = layoutParams
    params.width = paint.measureText(text.toString()).roundToInt() + paddingLeft + paddingRight
    layoutParams = params
}
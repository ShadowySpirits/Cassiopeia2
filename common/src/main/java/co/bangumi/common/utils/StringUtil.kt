package co.bangumi.common.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class StringUtil {

    companion object {
        private var dayFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
        private const val oneDay = 86400000

        fun dayOfWeek(day: Int): String {
            return dayFormatter.format(day * oneDay + 3 * oneDay)
        }

        private fun addPadding(string: String): String {
            return (if (string.length < 2) "0" else "") + string
        }

        fun microsecondFormat(ms: Long): String {
            return addPadding("" + TimeUnit.MILLISECONDS.toMinutes(ms)) +
                    ":" +
                    addPadding(
                        "" + (TimeUnit.MILLISECONDS.toSeconds(ms) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)))
                    )

        }
    }
}
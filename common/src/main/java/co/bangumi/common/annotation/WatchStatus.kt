package co.bangumi.common.annotation

import androidx.annotation.IntDef

const val DEFAULT = 0
const val WISH = 1
const val WATCHED = 2
const val WATCHING = 3
const val PAUSED = 4
const val ABANDONED = 5

@IntDef(DEFAULT, WISH, WATCHING, WATCHED, PAUSED, ABANDONED)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class WatchStatus
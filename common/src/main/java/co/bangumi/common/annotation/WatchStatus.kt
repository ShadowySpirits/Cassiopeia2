package co.bangumi.common.annotation

import androidx.annotation.IntDef

const val DEFAULT = -1
const val WISH = 1
const val WATCHED = 2
const val WATCHING = 3
const val PAUSE = 4
const val ABANDONED = 5

@IntDef(DEFAULT, WISH, WATCHING, WATCHED, PAUSE, ABANDONED)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class WatchStatus
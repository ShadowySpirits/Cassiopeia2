package co.bangumi.common.annotation

import androidx.annotation.IntDef


const val NOTICE = 1
const val RECOMMENDATION = 2

@IntDef(NOTICE, RECOMMENDATION)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class AnnounceType
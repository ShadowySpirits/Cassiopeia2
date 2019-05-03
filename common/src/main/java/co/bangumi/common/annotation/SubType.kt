package co.bangumi.common.annotation

import androidx.annotation.IntDef

const val ALL = -1
const val SUB = 1001
const val RAW = 1002

@IntDef(ALL, SUB, RAW)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class SubType
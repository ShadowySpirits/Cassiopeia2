package co.bangumi.framework.annotation

import androidx.annotation.IntDef

const val ERROR = -2
const val WARNING = -1
const val NORMAL = 0
const val SUCCESS = 1

@IntDef(ERROR, NORMAL, SUCCESS, WARNING)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class ToastType

package co.bangumi.framework.annotation

import androidx.annotation.IntDef

const val ERROR = -2
const val WARNING = -1
const val NORMAL = 0
const val INFO = 1
const val SUCCESS = 2

@IntDef(ERROR, INFO, SUCCESS, WARNING, NORMAL)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class ToastType

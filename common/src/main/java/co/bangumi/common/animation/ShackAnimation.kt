package co.bangumi.common.animation

import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation


fun shakeAnimation(counts: Int): Animation {
    val translateAnimation = TranslateAnimation(0f, 10f, 0f, 0f)
    translateAnimation.interpolator = CycleInterpolator(counts.toFloat())
    translateAnimation.duration = 1000
    return translateAnimation
}
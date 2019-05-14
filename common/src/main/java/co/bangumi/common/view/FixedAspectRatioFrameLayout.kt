package co.bangumi.common.view


import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import co.bangumi.common.R

class FixedAspectRatioFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var aspectRatio: Float = 0.toFloat()

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.FixedAspectRatioFrameLayout)
        aspectRatio = a.getFloat(R.styleable.FixedAspectRatioFrameLayout_aspectRatio, 1.6f)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val receivedWidth = MeasureSpec.getSize(widthMeasureSpec)
        val receivedHeight = MeasureSpec.getSize(heightMeasureSpec)

        val measuredWidth: Int
        val measuredHeight: Int
        val widthDynamic: Boolean
        if (heightMode == MeasureSpec.EXACTLY) {
            if (widthMode == MeasureSpec.EXACTLY) {
                widthDynamic = receivedWidth == 0
            } else {
                widthDynamic = true
            }
        } else if (widthMode == MeasureSpec.EXACTLY) {
            widthDynamic = false
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        if (widthDynamic) {
            // Width is dynamic.
            val w = (receivedHeight * aspectRatio).toInt()
            measuredWidth = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY)
            measuredHeight = heightMeasureSpec
        } else {
            // Height is dynamic.
            measuredWidth = widthMeasureSpec
            val h = (receivedWidth / aspectRatio).toInt()
            measuredHeight = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY)
        }
        super.onMeasure(measuredWidth, measuredHeight)
    }

}

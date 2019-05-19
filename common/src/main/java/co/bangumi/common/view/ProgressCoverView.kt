package co.bangumi.common.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import co.bangumi.common.R

class ProgressCoverView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress: Float = 0.toFloat()
    private var colorPaint: Paint? = null
    private var colorSecondaryPaint: Paint? = null

    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.ProgressCoverView)
        progress = a.getFloat(R.styleable.ProgressCoverView_progress, 0f)
        val color = a.getColor(R.styleable.ProgressCoverView_color, 0)
        val colorSecondary = a.getColor(R.styleable.ProgressCoverView_colorSecondary, 0)
        a.recycle()

        colorPaint = Paint()
        colorPaint!!.color = color

        colorSecondaryPaint = Paint()
        colorSecondaryPaint!!.color = colorSecondary
    }

    fun setColor(color: Int) {
        colorPaint = Paint()
        colorPaint!!.color = color
        postInvalidate()
    }

    fun setColorSecondary(colorSecondary: Int) {
        colorSecondaryPaint = Paint()
        colorSecondaryPaint!!.color = colorSecondary
        postInvalidate()
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        postInvalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val r1 = (measuredWidth * progress).toInt()
        canvas.drawRect(0f, 0f, r1.toFloat(), measuredHeight.toFloat(), colorPaint!!)
        canvas.drawRect(r1.toFloat(), 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), colorSecondaryPaint!!)
    }
}

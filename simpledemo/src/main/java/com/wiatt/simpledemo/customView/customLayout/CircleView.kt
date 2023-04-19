package com.wiatt.simpledemo.customView.customLayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.wiatt.common.dp

private val RADIUS = 80.dp
private val PADDING = 80.dp

class CircleView(context: Context, attrs: AttributeSet?)
    : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = ((PADDING + RADIUS) * 2).toInt()

        // 这段代码android系统已经实现了，即：resolveSize(size, widthMeasureSpec)
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val width = when(specWidthMode) {
            MeasureSpec.EXACTLY -> specWidthSize
            MeasureSpec.AT_MOST -> {
                if (size > specWidthSize)
                    specWidthSize
                else
                    size
            }
            MeasureSpec.UNSPECIFIED -> size
            else -> size
        }

        val height = resolveSize(size, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        println("getWidth， onDraw, = $width")
        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, paint)
    }
}
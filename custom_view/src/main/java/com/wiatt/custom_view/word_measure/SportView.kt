package com.wiatt.custom_view.word_measure

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.wiatt.common.dp
import com.wiatt.custom_view.R

private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
private val HIGHLIGHT_COLOR = Color.parseColor("#FF4081")
private val RING_WIDTH = 20.dp
private val RADIUS = 150.dp
class SportView(context: Context, attrs: AttributeSet?)
    : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 100.dp
        typeface = ResourcesCompat.getFont(context, R.font.font)
//        isFakeBoldText = true

        textAlign = Paint.Align.CENTER
    }
    private val bounds = Rect()
    private val fontMetrics = Paint.FontMetrics()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制圆环
        paint.style = Paint.Style.STROKE
        paint.color = CIRCLE_COLOR
        paint.strokeWidth = RING_WIDTH
        canvas.drawCircle(width/2f, height/2f, RADIUS, paint)

        //绘制进度条
        paint.color = HIGHLIGHT_COLOR
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(width/2f - RADIUS, height/2f - RADIUS,
            width/2f + RADIUS, height/2f + RADIUS,
            -90f, 225f, false, paint)

        //绘制文字，文字居中
        paint.style = Paint.Style.FILL
        //测量文字大小。适合静态文字
        /**
            paint.getTextBounds("abab", 0, "abab".length, bounds)
            canvas.drawText("abap", width/2f, height/2f - (bounds.top+bounds.bottom)/2f, paint)
         */
        //获取文字样式的大小，从而获取核心顶部和核心底部。适合动态文字
        paint.getFontMetrics(fontMetrics)
        canvas.drawText("abab", width/2f,
                height/2f - (fontMetrics.ascent+fontMetrics.descent)/2f, paint)

        //绘制文字，文字帖顶
        paint.textAlign = Paint.Align.LEFT
        // 适合静态文字
        paint.getTextBounds("abab", 0, "abab".length, bounds)
        canvas.drawText("abab", 0f - bounds.left, 0f - bounds.top, paint)
        //适合动态文字
        /**
            paint.getFontMetrics(fontMetrics)
            canvas.drawText("abab", 0f, 0f - fontMetrics.ascent, paint)
         */

        //绘制文字, 文字帖左
        paint.textSize = 15.dp
        paint.getTextBounds("abab", 0, "abab".length, bounds)
        canvas.drawText("abab", 0f - bounds.left, 0f - bounds.top, paint)
    }
}
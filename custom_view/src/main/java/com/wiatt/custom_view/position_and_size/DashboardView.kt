package com.wiatt.custom_view.position_and_size

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.wiatt.common.px
import kotlin.math.cos
import kotlin.math.sin

private val RADIUS = 150f.px
private val LENGTH = 80f.px
private const val OPEN_ANGLE = 120f
private val DASH_WIDTH = 2f.px
private val DASH_LENGH = 10f.px
private class DashboardView(context: Context?, attrs: AttributeSet?)
    : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val dash = Path()
    private lateinit var pathEffect: PathEffect

    init {
        paint.strokeWidth = 3f.px
        paint.style = Paint.Style.STROKE
        dash.addRect(0f, 0f,
            DASH_WIDTH,
            DASH_LENGH, Path.Direction.CCW)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        path.addArc(width/2f - RADIUS, height/2f - RADIUS,
                width / 2f + RADIUS, height / 2f + RADIUS,
                90 + OPEN_ANGLE /2f, 360f - OPEN_ANGLE
        )
        val pathMeasure = PathMeasure(path, false)
        pathEffect = PathDashPathEffect(dash, (pathMeasure.length - DASH_WIDTH)/20f, 0f, PathDashPathEffect.Style.ROTATE)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
        paint.pathEffect = pathEffect
        canvas.drawPath(path, paint)
        paint.pathEffect = null

        canvas.drawLine(width/2f, height/2f,
                width/2f + LENGTH *
                        cos(markToRadians(5)).toFloat(),
                height/2f + LENGTH *
                        sin(markToRadians(5)).toFloat(),
                paint)
    }

    private fun markToRadians(mark: Int): Double {
        return Math.toRadians(90.0 + OPEN_ANGLE /2f + (360 - OPEN_ANGLE)/20f * mark)
    }
}
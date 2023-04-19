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

private val RADIUS = 130f.px
private val OFFSET_LENGTH = 15f.px
private val ANGLES = floatArrayOf(60f, 90f, 150f, 60f)
private val COLORS = listOf(Color.parseColor("#C2185B"),
        Color.parseColor("#00ACC1"),
        Color.parseColor("#558B2F"),
        Color.parseColor("#5D4037"))
class PieView(context: Context?, attrs: AttributeSet?)
    : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        var startAngle = 0f
        for ((index, angle) in ANGLES.withIndex()) {
            paint.color = COLORS[index]
            if (index == 2) {
                canvas.save()
                canvas.translate(
                        (cos(angleToRadians(startAngle, angle)) * OFFSET_LENGTH).toFloat(),
                        (sin(angleToRadians(startAngle, angle)) * OFFSET_LENGTH).toFloat()
                )
            }
            canvas.drawArc(width/2f - RADIUS, height/2f - RADIUS,
                    width / 2f + RADIUS, height / 2f + RADIUS,
                    startAngle, angle, true, paint)
            startAngle += angle
            if (index == 2) {
                canvas.restore()
            }
        }
    }

    private fun angleToRadians(startAngle: Float, angle: Float): Double {
        return Math.toRadians((startAngle + angle/2f).toDouble())
    }
}
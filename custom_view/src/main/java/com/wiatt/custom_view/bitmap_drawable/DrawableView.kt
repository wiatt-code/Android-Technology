package com.wiatt.custom_view.bitmap_drawable

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import com.wiatt.common.dp

class DrawableView(context: Context?, attrs: AttributeSet?)
    : View(context, attrs) {
    val drawable = MeshDrawable()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 用drawable时一定要要设置边界
        drawable.setBounds(50.dp.toInt(), 50.dp.toInt(), width, height)
        drawable.draw(canvas)
    }
}
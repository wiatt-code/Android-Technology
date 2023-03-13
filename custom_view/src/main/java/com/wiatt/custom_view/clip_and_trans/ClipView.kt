package com.wiatt.custom_view.clip_and_trans

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.wiatt.common.dp
import com.wiatt.custom_view.R

private val BITMAP_SIZE = 200.dp
private val BITMAP_PADDING = 50.dp
private val BITMAP_PADDING_2 = 200.dp

class ClipView(context: Context, attrs: AttributeSet?)
    : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(BITMAP_SIZE.toInt())
    private val clipped = Path().apply {
        addOval(BITMAP_PADDING, BITMAP_PADDING_2,
            BITMAP_PADDING + BITMAP_SIZE,
            BITMAP_PADDING_2 + BITMAP_SIZE,
            Path.Direction.CCW)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        // 裁剪矩形
        canvas.clipRect(BITMAP_PADDING, BITMAP_PADDING,
            BITMAP_PADDING + BITMAP_SIZE/2,
            BITMAP_PADDING + BITMAP_SIZE/2)
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING, paint)
        canvas.restore()
        // 裁剪用path自定义的图形（此处是圆形）
        canvas.clipPath(clipped)
        canvas.drawBitmap(bitmap, BITMAP_PADDING, BITMAP_PADDING_2, paint)
    }


    private fun getAvatar(width: Int): Bitmap {
        val options: BitmapFactory.Options =  BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }
}


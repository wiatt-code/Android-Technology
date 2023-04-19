package com.wiatt.custom_view.word_measure

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import com.wiatt.common.dp
import com.wiatt.custom_view.R

private val IMAGE_SIZE = 150.dp
private val IMAGE_PADDING = 100.dp

class MultilineTextView(context: Context, attrs: AttributeSet): ScrollView(context, attrs) {

    val text = "Lacinia quisque etiam, dictum curabitur, scelerisque tempor tortor ad dolor aliquam gravida. Hac ornare litora, lorem malesuada dictum commodo. Dui convallis pharetra tempor, velit ut laoreet, aliquamtorquent habitasse erat vehicula aliquam. Habitasse aenean class, dictumst laoreet, ut euismod sodales condimentum lectus. Lorem dapibus himenaeos, elit sapien nisl eu. Enim massa facilisis etiam, integer cubilia amet nam id. Leo posuere diam dictumst, quisque ultrices convallis, sagittis feugiat sed ut mi eleifend est class. Accumsan eget odio, curabitur vivamus nulla habitant.\n" +
            "\n" +
            "Curabitur convallis, dolor pharetra nisi lacinia. Nunc facilisis quisque, habitasse cubilia lacinia faucibus diam. Augue eu hac, congue curabitur, lectus integer iaculis tempus volutpat fames. Tempus pulvinar, ipsum ultrices velit. Semper mollis, ut vitae sed pretium.\n"
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp
    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp
    }
    private val bitmap = getAvatar(IMAGE_SIZE.toInt())
    private val fontMetrics = Paint.FontMetrics()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //纯文本换行
        /**
            val staticLayout = StaticLayout(text, textPaint, width,
                Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false)
            staticLayout.draw(canvas)
         */

        canvas.drawBitmap(bitmap, width - IMAGE_SIZE, IMAGE_PADDING, paint)
        paint.getFontMetrics(fontMetrics)
        val measureWidth = floatArrayOf()
        var start = 0
        var count: Int
        // 文字基准线位置
        var verticalOffset = 0f-fontMetrics.top
        var maxWidth: Float
        while (start < text.length) {
            // 图片碰撞检测
            // verticalOffset + fontMetrics.bottom < 图片顶部高度
            // verticalOffset + fontMetrics.top > 图片底部高度
            maxWidth = if(verticalOffset + fontMetrics.bottom < IMAGE_PADDING
                    || verticalOffset + fontMetrics.top > IMAGE_PADDING + IMAGE_SIZE) {
                width.toFloat()
            } else {
                width.toFloat() - IMAGE_SIZE
            }
            // 一行字符数测量
            count = paint.breakText(text, start, text.length,
                    true, maxWidth, measureWidth)
            canvas.drawText(text, start, start + count,
                    0f, verticalOffset, paint)
            start += count
            verticalOffset += paint.fontSpacing
        }
    }

    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.avatar_programmer, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.mipmap.avatar_programmer, options)
    }
}
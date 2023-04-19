package com.wiatt.simpledemo.customView.xfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.wiatt.common.px
import com.wiatt.simpledemo.R

private val IMAGE_WIDTH = 200f.px
private val IMAGE_PADDING = 20f.px
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

class AvatarView(context: Context, attrs: AttributeSet): View(context, attrs) {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bouds = RectF(
        IMAGE_PADDING,
        IMAGE_PADDING,
        IMAGE_PADDING + IMAGE_WIDTH, IMAGE_PADDING + IMAGE_WIDTH
    )

    override fun onDraw(canvas: Canvas) {
        /**
         * 设置“离屏缓冲”
         * bouds: 离屏缓冲的大小。该操作耗费资源，bouds尽可能的小
         */
        val count = canvas.saveLayer(bouds, null)
        // 底板大小
        canvas.drawOval(
            IMAGE_PADDING,
            IMAGE_PADDING,
            IMAGE_PADDING + IMAGE_WIDTH, IMAGE_PADDING + IMAGE_WIDTH, paint)
        // 设置xfermode的模式
        paint.xfermode = XFERMODE
        // 绘制图片
        canvas.drawBitmap(getAvatar(IMAGE_WIDTH.toInt()),
            IMAGE_PADDING,
            IMAGE_PADDING, paint)
        // 还原xfermode的模式
        paint.xfermode = null
        /**
         * 将离屏缓冲区放回原位
         * count：回到哪一步骤
         */
        canvas.restoreToCount(count)
    }

    fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.avatar_programmer, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.mipmap.avatar_programmer, options)
    }
}
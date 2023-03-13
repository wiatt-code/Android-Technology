package com.wiatt.custom_view.scalableImage

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import android.widget.Scroller
import androidx.core.animation.doOnEnd
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.wiatt.common.dp
import com.wiatt.custom_view.R

private val IMAGE_SIZE = 300.dp.toInt()
private const val EXTRA_SCALE_FACTOR = 1.5f

class ScalableImageView(context: Context, attrs: AttributeSet?): View(context, attrs){

    private val bitmap = getAvatar(IMAGE_SIZE)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var smallScale = 0f
    private var bigScale = 0f
    private val myGestureListener = MyGestureListener()
    private val myScaleGestureListener = MyScaleGestureListener()
    private val myFlingRunner = MyFlingRunner()
    private val gestureDetector = GestureDetectorCompat(context, myGestureListener)
    private val scaleGestureDetector = ScaleGestureDetector(context, myScaleGestureListener)
    private var big = false
    private var currentScale = 0f
        set(value) {
            field = value
            invalidate()
        }
    private val scaleAnimator: ObjectAnimator = ObjectAnimator.ofFloat(this, "currentScale", smallScale, bigScale)
    private val scroller = OverScroller(context)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (w - bitmap.width) / 2f
        originalOffsetY = (h - bitmap.width) / 2f
        if (bitmap.width / bitmap.height.toFloat() > w / h.toFloat()) {
            smallScale = w / bitmap.width.toFloat()
            bigScale = h / bitmap.height.toFloat() * EXTRA_SCALE_FACTOR
        } else {
            smallScale = h / bitmap.height.toFloat()
            bigScale = w / bitmap.width.toFloat() * EXTRA_SCALE_FACTOR
        }
        currentScale = smallScale
        scaleAnimator.setFloatValues(smallScale, bigScale)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            gestureDetector.onTouchEvent(event)
        }
        return true
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    private fun getAvatar(width: Int): Bitmap {
        val options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }

    private fun fixOffsets() {
        offsetX = offsetX.coerceAtMost((bitmap.width * bigScale - width) / 2)
                .coerceAtLeast(-(bitmap.width * bigScale - width) / 2)
        offsetY = offsetY.coerceAtMost((bitmap.height * bigScale - height) / 2)
                .coerceAtLeast(-(bitmap.height * bigScale - height) / 2)
    }

    inner class MyGestureListener: GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
        ): Boolean {
            if (big) {
                scroller.fling(offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                        (-(bitmap.width * bigScale - width) / 2).toInt(),
                        ((bitmap.width * bigScale - width) / 2).toInt(),
                        (-(bitmap.height * bigScale - height) / 2).toInt(),
                        ((bitmap.height * bigScale - height) / 2).toInt(),
                        60.dp.toInt(), 60.dp.toInt())
                // postOnAnimation: 在下一帧调用传入的代码块
                // postOnAnimation与post比较
                // 相同点：都会将代码块推到主线程执行
                // 不同点：post会立即执行，postOnAnimation会等到下一帧再执行
                // ViewCompat：是view的辅助工具类
                // ViewCompat.postOnAnimation 会判断postOnAnimation是否可用，
                // 如果不可用，则用postDelayed(Runnable action, long delayMillis)代替
                ViewCompat.postOnAnimation(this@ScalableImageView, myFlingRunner)
            }
            return true
        }

        override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent?,
                distanceX: Float,
                distanceY: Float
        ): Boolean {
            if (big) {
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffsets()
                invalidate()
            }
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            big = !big
            if (big) {
                // 计算放大时的初试偏移
                // 大图距触摸点距小图触摸点的偏移量 = 手指距中心点的实际偏移量 * （放大倍数的比率 - 1）
                offsetX = (e.x - width / 2f) * (1 - bigScale / smallScale)
                offsetY = (e.y - height / 2) * (1- bigScale / smallScale - 1)
                fixOffsets()
                scaleAnimator.start()
            } else {
                scaleAnimator.reverse()
            }
            return true
        }
    }

    inner class MyScaleGestureListener : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {

            offsetX = (detector.focusX - width / 2f) * (1 - bigScale / smallScale)
            offsetY = (detector.focusY - height / 2f) * (1- bigScale / smallScale - 1)
            fixOffsets()
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val tmpCurrentScale = currentScale * detector.scaleFactor
            return if (tmpCurrentScale < smallScale || tmpCurrentScale > bigScale) {
                false
            } else {
                currentScale = tmpCurrentScale
                true
            }
        }

    }

    inner class MyFlingRunner : Runnable {
        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                ViewCompat.postOnAnimation(this@ScalableImageView, this)
            }
        }
    }
}
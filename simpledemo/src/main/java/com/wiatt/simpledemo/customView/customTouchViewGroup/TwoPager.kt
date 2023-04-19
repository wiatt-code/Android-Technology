package com.wiatt.simpledemo.customView.customTouchViewGroup

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.children
import kotlin.math.abs

class TwoPager(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    // 按下点的X轴坐标
    private var downX = 0f
    // 按下点的Y轴坐标
    private var downY = 0f
    // 记录按下时，scrollX的值,即初始偏移
    // scrollX的值总等于View的左边缘与View内容左边缘的水平距离
    // scrollY的值总等于View的上边缘与View内容上边缘的垂直距离
    private var downScrollX = 0f
    // 是否正在滑动
    private var scrolling = false
    private var overScroller = OverScroller(context)
    // 速度跟踪器
    private val velocityTracker = VelocityTracker.obtain()
    private val viewConfiguration = ViewConfiguration.get(context)
    // view的最小滑行速度
    private var minVelocity = viewConfiguration.scaledMinimumFlingVelocity
    // view的最大滑行速度
    private var maxVelocity = viewConfiguration.scaledMaximumFlingVelocity
    // 最小滑动距离判定阈值，当手指滑动距离超过这个阈值，可以判定为用户希望滚动界面
    private var pagingSlop = viewConfiguration.scaledPagingTouchSlop

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        val childTop = 0
        var childRight = width
        val childBottom = height
        // 遍历所有子View
        for (child in children) {
            child.layout(childLeft, childTop, childRight, childBottom)
            childLeft += width
            childRight += height
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        var result = false
        when(event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                scrolling = false
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                if(!scrolling) {
                    val dx = downX - event.x
                    if (abs(dx) > pagingSlop) {
                        scrolling = true
                        // 通知父控件，在当前事件序列中不要再拦截触摸事件
                        parent.requestDisallowInterceptTouchEvent(true)
                        // 给子View返回true，表示拦截子View的触摸事件
                        result = true
                    }
                }
            }
        }
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        when (event.actionMasked) {
            // 记录手指按下时的状态
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            // 计算偏移并滑动
            MotionEvent.ACTION_MOVE -> {
                val dx = (downX - event.x + downScrollX).toInt()
                    .coerceAtLeast(0)
                    .coerceAtMost(width)
                // 对于scrollTo，往左划应填入正值，往右滑应填入负值
                // 因为它的物理模型不一样，传入的值表示要从子View的什么位置开始显示这个子View
                scrollTo(dx, 0)
            }
            // 根据抬起时的滑动状态，选择显示哪一个view
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000, maxVelocity.toFloat())
                val vx = velocityTracker.xVelocity
                val scrollX = scrollX
                val targetPage = if (abs(vx) < minVelocity) {
                    if (scrollX > width / 2) { 1 } else { 0 }
                } else {
                    if (vx < 0) { 1 } else { 0 }
                }
                val scrollDistance = if (targetPage == 1) {
                    width - scrollX
                } else {
                    -scrollX
                }
                // 给定初试位置和滑动距离，进行滑动，滑动速度会自行计算
                overScroller.startScroll(getScrollX(), 0, scrollDistance, 0)
                // 在下一帧标记代码失效
                postInvalidateOnAnimation()
            }
        }
        return true
    }

    override fun computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY)
            postInvalidateOnAnimation()
        }
    }
}
package com.wiatt.custom_view.CustomViewTouchDrag

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.customview.widget.ViewDragHelper
import com.google.android.material.appbar.AppBarLayout
import javax.sql.RowSetWriter

private const val COLUMNS = 2
private const val ROWS = 3

class DragHelperGridView(context: Context, attrs: AttributeSet?)
    : ViewGroup(context, attrs) {
    private var dragHelper = ViewDragHelper.create(this, DragCallback())

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = specWidth / COLUMNS
        val childHeight = specHeight / ROWS
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY))
        setMeasuredDimension(specWidth, specHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft: Int
        var childTop: Int
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS
        for ((index, child) in children.withIndex()) {
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight
            child.layout(childLeft, childTop,
                childLeft + childWidth, childTop + childHeight)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event)
        return true
    }

    //postInvalidateOnAnimation()被调用时，回调该方法
    override fun computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    private inner class DragCallback : ViewDragHelper.Callback() {

        var captureLeft = 0f
        var captureTop = 0f

        // 是否要拖拽控件，
        // 返回true表示拖拽，返回false表示不拖拽
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun onViewDragStateChanged(state: Int) {
            if (state == ViewDragHelper.STATE_IDLE) {
                val capturedView = dragHelper.capturedView
                if (capturedView != null) {
                    capturedView.elevation--
                }
            }
        }

        // left：触摸点的横向偏移量
        // dx：
        // 返回值：控件的实际横向偏移量，默认值为0
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }

        // top：触摸点的纵向偏移量
        // dx：
        // 返回值：控件的实际纵向偏移量，默认值为0
        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        // 当view被抓住的时候调用，拖拽的起始点
        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            // elevation：1z轴高度，这个变量的增加，可以盖在其他控件上方
            capturedChild.elevation = elevation + 1
            captureLeft = capturedChild.left.toFloat()
            captureTop = capturedChild.top.toFloat()
        }

        // view的坐标改变时调用
        override fun onViewPositionChanged(
            changedView: View, left: Int, top: Int, dx: Int, dy: Int) {

        }

        // view被释放时调用
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            dragHelper.settleCapturedViewAt(captureLeft.toInt(), captureTop.toInt())
            postInvalidateOnAnimation()
        }
    }

}
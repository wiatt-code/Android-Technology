package com.wiatt.custom_view.customViewTouchDrag

import android.content.Context
import android.util.AttributeSet
import android.view.*
import androidx.core.view.*
import androidx.customview.widget.ViewDragHelper
import com.wiatt.common.dp
import com.wiatt.custom_view.R

private const val COLUMNS = 3
private val MARGIN = 5.dp.toInt()

class DragHelperViewGroup(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private var rows = 0
    private var childWidth = 0
    private var childHeight = 0
    private var orderedChildren: MutableList<View> = ArrayList()
    private var myDragListener: OnDragListener = MyDragListener()
    private var myDragHelper = ViewDragHelper.create(this, DragCallback())

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        println("DragHelperViewGroup, onMeasure")
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        rows = (childCount + COLUMNS - 1) / COLUMNS
        childWidth = (specWidth - (COLUMNS + 1) * MARGIN) / COLUMNS
        childHeight = (specHeight - (COLUMNS + 1) * MARGIN) / rows
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY))
        setMeasuredDimension(specWidth, specHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        println("DragHelperViewGroup, onLayout")
        var childLeft: Int
        var childTop: Int
        for ((index, child) in  orderedChildren.withIndex()) {
            childLeft = index % COLUMNS * childWidth + (index % COLUMNS + 1) * MARGIN
            childTop = index / COLUMNS * childHeight + (index / COLUMNS + 1) * MARGIN
            child.layout(childLeft, childTop,
                    childLeft + childWidth, childTop + childHeight)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        println("DragHelperViewGroup, onFinishInflate")
        for (child in children) {
            orderedChildren.add(child)
            child.setOnDragListener(myDragListener)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return myDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        myDragHelper.processTouchEvent(event)
        return true
    }

    //postInvalidateOnAnimation()被调用时，回调该方法
    override fun computeScroll() {
        if (myDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    // 计算当前坐标属于哪个方块
    private fun calculateIndex(targetLeft: Int, targetTop: Int): Int {
        var targetIndex = -1
        var xIndex = -1
        var yIndex = -1
        for (i in 0 until COLUMNS) {
            val left = i % COLUMNS * childWidth + (i % COLUMNS + 1) * MARGIN
            val right = left + childWidth
            println("DragHelperViewGroup, targetLeft = $targetLeft, left = $left, right = $right")
            if (targetLeft in left..right) {
                xIndex = i
                break
            }
        }
        for (i in 0 until rows) {
            val top = i % rows * childHeight + (i % rows + 1) * MARGIN
            val bottom = top + childHeight
            println("DragHelperViewGroup, targetTop = $targetTop, top = $top, bottom = $bottom")
            if (targetTop in top..bottom) {
                yIndex = i
                break
            }
        }
        println("DragHelperViewGroup, xIndex = $xIndex, yIndex = $yIndex")
        targetIndex = if (xIndex == -1 || yIndex == -1) {
            -1
        } else {
            yIndex * COLUMNS + xIndex
        }
        return targetIndex
    }

    private fun sort(sourceIndex: Int, targetIndex: Int, child: View) {
        orderedChildren.removeAt(sourceIndex)
        orderedChildren.add(targetIndex, child)
        for ((index, child) in orderedChildren.withIndex()) {
            var childLeft = index % COLUMNS * childWidth + (index % COLUMNS + 1) * MARGIN
            var childTop = index / COLUMNS * childHeight + (index / COLUMNS + 1) * MARGIN
            child.layout(childLeft, childTop,
                    childLeft + childWidth, childTop + childHeight)
        }
    }

    private inner class MyDragListener : OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DRAG_ENTERED -> {
                    println("DragHelperViewGroup, ACTION_DRAG_ENTERED, id = ${v.id}")
                }
                DragEvent.ACTION_DROP -> {
                    val colorData = event.clipData.getItemAt(0).text.toString()
                    println("DragHelperViewGroup, ACTION_DROP, id = ${v.id}, color = $colorData")
                    when (colorData) {
                        resources.getString(R.string.red) -> {
                            v.background = resources.getDrawable(R.color.red, null)
                        }
                        resources.getString(R.string.orange) -> {
                            v.background = resources.getDrawable(R.color.orange, null)
                        }
                        resources.getString(R.string.yellow) -> {
                            v.background = resources.getDrawable(R.color.yellow, null)
                        }
                        resources.getString(R.string.green) -> {
                            v.background = resources.getDrawable(R.color.green, null)
                        }
                        resources.getString(R.string.blue) -> {
                            v.background = resources.getDrawable(R.color.blue, null)
                        }
                        resources.getString(R.string.indigo) -> {
                            v.background = resources.getDrawable(R.color.indigo, null)
                        }
                        resources.getString(R.string.purple) -> {
                            v.background = resources.getDrawable(R.color.purple, null)
                        }
                    }
                }
            }
            return true
        }
    }

    private inner class DragCallback : ViewDragHelper.Callback() {

        var captureLeft = 0f
        var captureTop = 0f
        var newLeft = 0
        var newTop = 0

        // 是否要拖拽控件，
        // 返回true表示拖拽，返回false表示不拖拽
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun onViewDragStateChanged(state: Int) {
            if (state == ViewDragHelper.STATE_IDLE) {
                val capturedView = myDragHelper.capturedView
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
            newLeft = left + changedView.width / 2
            newTop  = top + changedView.height / 2
        }

        // view被释放时调用
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            var sourceIndex = 0
            for ((index, child) in orderedChildren.withIndex()) {
                if (releasedChild === child) {
                    sourceIndex = index
                }
            }
            var targetIndex = calculateIndex(newLeft, newTop)
            println()
            if (targetIndex == -1
                    || targetIndex >= orderedChildren.size
                    || targetIndex == sourceIndex) {
                println("DragHelperViewGroup, targetIndex = $targetIndex")
                myDragHelper.settleCapturedViewAt(captureLeft.toInt(), captureTop.toInt())
                postInvalidateOnAnimation()
            } else {
                sort(sourceIndex, targetIndex, releasedChild)
                println("DragHelperViewGroup, sourceIndex = $sourceIndex, targetIndex = $targetIndex")
            }
        }
    }
}
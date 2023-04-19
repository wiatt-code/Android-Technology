package com.wiatt.simpledemo.customView.customViewTouchDrag

import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

private const val COLUMNS = 2
private const val ROWS = 3

class DragListenerGridView(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    private var dragListener: OnDragListener = MyDragListener()
    private var draggedView: View? = null
    private var orderedChildren: MutableList<View> = ArrayList()

    init {
        isChildrenDrawingOrderEnabled = true
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (child in children) {
            orderedChildren.add(child)
            child.setOnLongClickListener { v ->
                draggedView = v
                // 回调监听中，第一个参数可以跨进程，只有在DragEvent.ACTION_DROP时才能拿到，
                // 第三个参数不能跨进程，但随时都能拿到
                v.startDrag(null, DragShadowBuilder(v), v, 0)
                false
            }
            // 一个子View被拖拽，其他子View都会收到回调
            child.setOnDragListener(dragListener)
        }
    }

    // 对于自定义View可以重写该方法实现拖拽监听
    // 无论是否设置了OnDragListener
    override fun onDragEvent(event: DragEvent?): Boolean {
        return super.onDragEvent(event)
    }

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
            child.layout(0, 0, childWidth, childHeight)
            child.translationX = childLeft.toFloat()
            child.translationY = childTop.toFloat()
        }
    }

    private fun sort(targetView: View) {
        var draggedIndex = -1
        var targetIndex = -1
        for ((index, child) in orderedChildren.withIndex()) {
            if (targetView === child) {
                targetIndex = index
            } else if (draggedView === child) {
                draggedIndex = index
            }
        }
        orderedChildren.removeAt(draggedIndex)
        orderedChildren.add(targetIndex, draggedView!!)
        var childLeft: Int
        var childTop: Int
        var childWidth = width / COLUMNS
        var childHeight = height / ROWS
        for ((index, child) in orderedChildren.withIndex()) {
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight
            child.animate()
                .translationX(childLeft.toFloat())
                .translationY(childTop.toFloat())
                .duration = 150
        }
    }

    // 拖拽监听器
    private inner class MyDragListener: OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                // 有一个控件被拖拽
                DragEvent.ACTION_DRAG_STARTED -> if (event.localState === v) {
                    v.visibility = View.INVISIBLE
                }
                // 拖拽事件中，触摸点进入某个View的区域内
                DragEvent.ACTION_DRAG_ENTERED -> if (event.localState !== v) {
                    sort(v)
                }
                // 拖拽事件中，触摸点离开某个View的区域
                DragEvent.ACTION_DRAG_EXITED -> {
                }
                DragEvent.ACTION_DRAG_ENDED -> if (event.localState === v) {
                    v.visibility = View.VISIBLE
                }
            }
            return true
        }
    }
}
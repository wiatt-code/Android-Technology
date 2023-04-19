package com.wiatt.simpledemo.customView.customViewTouchDrag

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.view.children

private const val ROWS = 3
private const val STRING_NAME = "name"

class DragListenerViewGroup(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {
    private var columns = 0
    private var childWidth = 0
    private var childHeight = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        columns = (childCount + ROWS - 1) / ROWS
        childWidth = specWidth / columns
        childHeight = specHeight / ROWS
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY))
        setMeasuredDimension(specWidth, specHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft: Int
        var childTop: Int
        for ((index, child) in  children.withIndex()) {
            childLeft = index % columns * childWidth
            childTop = index / columns * childHeight
            child.layout(childLeft, childTop,
                    childLeft + childWidth, childTop + childHeight)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (child in children) {
            child.setOnLongClickListener { v ->
                val colorData = ClipData.newPlainText(STRING_NAME, v.contentDescription)
                v.startDrag(colorData, DragShadowBuilder(v), v, 0)
                false
            }
        }
    }

}
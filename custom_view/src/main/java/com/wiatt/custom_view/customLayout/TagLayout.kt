package com.wiatt.custom_view.customLayout

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

class TagLayout(context: Context, attrs: AttributeSet?)
    : ViewGroup(context, attrs) {

    private val childrenBounds = mutableListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        /*val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)*/

        var widthUsed = 0
        var heightUsed = 0
        var lineWidthUsed = 0
        var lineMaxHeight = 0
        var specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        var specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        // 计算子view的尺寸
        for ((index, child) in children.withIndex()) {

            // 测量子View的大小，与开发者意见和父控件约束有关，
            // measureChildWithMargins()函数为系统提供的测量方案，和下述方案大同小异
            /*
            // 开发者意见
            val layoutParams = child.layoutParams

            var childWidthSpecMode = 0
            var childWidthSpecSize = 0
            when (layoutParams.width) {
                LayoutParams.MATCH_PARENT ->
                    when (widthSpecMode) {
                        MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
                            childWidthSpecMode = MeasureSpec.EXACTLY
                            childWidthSpecSize = widthSpecSize - widthUsed
                        }
                        MeasureSpec.UNSPECIFIED -> {
                            childWidthSpecMode = MeasureSpec.UNSPECIFIED
                            childWidthSpecSize = 0
                        }
                    }
                LayoutParams.WRAP_CONTENT ->
                    when (widthMeasureSpec) {
                        MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
                            childWidthSpecMode = MeasureSpec.AT_MOST
                            childWidthSpecSize = widthSpecSize - widthUsed
                        }
                        MeasureSpec.UNSPECIFIED -> {
                            childWidthSpecMode = MeasureSpec.UNSPECIFIED
                            childWidthSpecSize = 0
                        }
                    }
                else -> {
                    childWidthSpecMode = MeasureSpec.EXACTLY
                    childWidthSpecSize = layoutParams.width
                }
            }
            child.measure(childWidthSpec, childHeightSpec)
            */
            //
            /**
             * 此处widthUsed参数必须是0，而不能是lineWidthUsed的原因：
             * 目的在于必须计算出一个完整标签的长度值child.measuredWidth，提供给后续判断逻辑。
             * 如果该参数使用了lineWidthUsed，则当前行剩余空间不足时，child.measuredWidth会被赋值为剩余空间长度或者是0。
             * 这样就导致最右侧的标签变形，折行的判断逻辑也需要修改。
             * 如果标签本身宽度大于屏幕宽度，则与widthUsed参数无关。由于受到父控件约束，所以标签宽度只能与父控件等宽，此处即屏幕宽度
             */
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            // 折行判断
            if (specWidthMode != MeasureSpec.UNSPECIFIED &&
                    lineWidthUsed + child.measuredWidth > specWidthSize) {
                heightUsed += lineMaxHeight
                lineWidthUsed = 0
                lineMaxHeight = 0
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }
            if (index >= childrenBounds.size) {
                childrenBounds.add(Rect())
            }
            val childBounds = childrenBounds[index]
            childBounds.set(lineWidthUsed, heightUsed, lineWidthUsed + child.measuredWidth,
                heightUsed + child.measuredHeight)
            lineWidthUsed += child.measuredWidth
            widthUsed = max(widthUsed, lineWidthUsed)
            lineMaxHeight = max(lineMaxHeight, child.measuredHeight)

        }
        // 计算自己的尺寸
        val selfWidth = widthUsed
        val selfHeight = heightUsed + lineMaxHeight
        println("TagLayout: selfWidth = $selfWidth, selfHeight = $selfHeight")
        setMeasuredDimension(selfWidth, selfHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index, child) in children.withIndex()) {
            val childBounds = childrenBounds[index]
            child.layout(childBounds.left, childBounds.top,
                childBounds.right, childBounds.bottom)
        }
    }

    // 报错：java.lang.ClassCastException: android.view.ViewGroup$LayoutParams cannot be cast to android.view.ViewGroup$MarginLayoutParams
    // 关于上述报错的解决方案
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }
}
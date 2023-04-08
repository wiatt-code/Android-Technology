package com.wiatt.simpledemo.constraintLayoutDemo

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout

class CircularRevealHelper(context: Context, attrs: AttributeSet)
    : ConstraintHelper(context, attrs) {
    override fun updatePostLayout(container: ConstraintLayout?) {
        super.updatePostLayout(container)
        referencedIds.forEach {

        }
    }

    override fun updatePostMeasure(container: ConstraintLayout?) {
        super.updatePostMeasure(container)
    }

    override fun updatePreLayout(container: ConstraintLayout?) {
        super.updatePreLayout(container)
    }
}
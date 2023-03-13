package com.wiatt.custom_view.customTouch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.RelativeLayout

class TouchLayout2(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {
    var count = 0
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        println("Touch, TouchLayout---2, onInterceptTouchEvent, action = ${ev.actionMasked}")
        return  if (count == 3 && ev.actionMasked == MotionEvent.ACTION_MOVE) {
            true
        } else {
            count++
            false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        println("Touch, TouchLayout---2, onTouchEvent, action = ${event.actionMasked}")
        return false
    }
}
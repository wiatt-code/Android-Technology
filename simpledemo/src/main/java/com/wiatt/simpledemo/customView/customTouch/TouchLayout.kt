package com.wiatt.simpledemo.customView.customTouch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.RelativeLayout

class TouchLayout(context: Context, attrs: AttributeSet?)
    : RelativeLayout(context, attrs) {

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        println("Touch, TouchLayout, onInterceptTouchEvent, action = ${ev.actionMasked}")
        return ev.actionMasked == MotionEvent.ACTION_MOVE
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        println("Touch, TouchLayout, onTouchEvent, action = ${event.actionMasked}")
        return false
    }
}
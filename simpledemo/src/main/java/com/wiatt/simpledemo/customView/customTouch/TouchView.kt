package com.wiatt.simpledemo.customView.customTouch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class TouchView(context: Context, attrs: AttributeSet?)
    : View(context, attrs) {

    override fun onTouchEvent(event: MotionEvent): Boolean {
        println("Touch, TouchView, onTouchEvent, action = ${event.actionMasked}")
        return true

    }
}
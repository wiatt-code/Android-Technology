package com.wiatt.audioVideo.customView

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar

class ProgressSeekBar(context: Context, attrs: AttributeSet?):
    AppCompatSeekBar(context, attrs) {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }
}
package com.wiatt.common

import java.util.Formatter
import java.util.Locale


object TimeUtil {

    var mFormatBuilder = StringBuilder()
    var mFormatter = Formatter(mFormatBuilder, Locale.getDefault())

    fun stringForTime(timeSec: Int): String {
        var seconds = timeSec % 60
        var minutes = (timeSec / 60) % 60
        var hours = timeSec / 3600
        mFormatBuilder.setLength(0)
        return if (hours > 0) {
            mFormatter.format("%d:%02d:%02d",hours,minutes,seconds).toString()
        } else {
            mFormatter.format("%02d:%02d",minutes,seconds).toString()
        }
    }
}
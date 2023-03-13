package com.wiatt.common

import android.text.BoringLayout
import android.util.Log

object LogTool{
    private const val TAG = "manuscript"
    private var debug: Boolean = true
    public fun e(msg: String) {
        if (debug) {
            Log.e(TAG, msg)
        }
    }
}
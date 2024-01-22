package com.wiatt.common

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.Size
import android.view.WindowInsets
import android.view.WindowManager
import android.view.WindowMetrics


/**
 * @author wiatt
 * @description:
 */
object ScreenSizeUtil {

    fun getScreenWidth(context: Context): Int {
        val manager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics: WindowMetrics = manager.currentWindowMetrics
            val windowInsets: WindowInsets = metrics.windowInsets
            val insets = windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.navigationBars()
                        or WindowInsets.Type.displayCutout()
            )

            val insetsWidth = insets.right + insets.left
            val insetsHeight = insets.top + insets.bottom

            val bounds: Rect = metrics.bounds
            val legacySize = Size(
                bounds.width() - insetsWidth,
                bounds.height() - insetsHeight
            )
            return legacySize.width
        } else {
            val manager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm: DisplayMetrics = DisplayMetrics()
            manager.defaultDisplay.getMetrics(dm)
            dm.widthPixels
        }
    }

    fun getScreenHeight(context: Context): Int {
        val manager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics: WindowMetrics = manager.currentWindowMetrics
            val windowInsets: WindowInsets = metrics.windowInsets
            val insets = windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.navigationBars()
                        or WindowInsets.Type.displayCutout()
            )

            val insetsWidth = insets.right + insets.left
            val insetsHeight = insets.top + insets.bottom

            val bounds: Rect = metrics.bounds
            val legacySize = Size(
                bounds.width() - insetsWidth,
                bounds.height() - insetsHeight
            )
            return legacySize.height
        } else {
            val manager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm: DisplayMetrics = DisplayMetrics()
            manager.defaultDisplay.getMetrics(dm)
            dm.heightPixels
        }
    }
}
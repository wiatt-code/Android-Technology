package com.wiatt.common

import android.content.res.Resources
import android.util.TypedValue

/***
 * 这个类有问题，dp转px，可以这么转；px转dp不可以
 */
val Float.px
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        this, Resources.getSystem().displayMetrics)

val Float.dp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
        this, Resources.getSystem().displayMetrics)

val Int.dp
    get() = this.toFloat().dp
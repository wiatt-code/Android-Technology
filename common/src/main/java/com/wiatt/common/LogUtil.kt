package com.wiatt.common

import timber.log.Timber

object LogUtil{
    private const val TAG = "Android-Technology"

    @JvmStatic
    fun init() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }

    @JvmStatic
    fun d(tag: String = TAG, msg: String) {
        Timber.tag(tag).d(msg)
    }
    @JvmStatic
    fun i(tag: String = TAG, msg: String) {
        Timber.tag(tag).i( msg)
    }
    @JvmStatic
    fun e(tag: String = TAG, msg: String) {
        Timber.tag(tag).e(msg)
    }
    @JvmStatic
    fun v(tag: String = TAG, msg: String) {
        Timber.tag(tag).v( msg)
    }
    @JvmStatic
    fun w(tag: String = TAG, msg: String) {
        Timber.tag(tag).w(msg)
    }
}

class CrashReportingTree: Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    }
}
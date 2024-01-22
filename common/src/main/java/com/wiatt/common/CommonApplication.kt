package com.wiatt.common

import android.app.Application

open class CommonApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        mApplication = this
        LogUtil.init()
    }

    companion object {
        var mApplication: CommonApplication? = null
    }
}
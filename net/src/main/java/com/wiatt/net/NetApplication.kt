package com.wiatt.net

import android.app.Application
import android.content.Context

class NetApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }

    companion object {
        var mApplication: NetApplication? = null
    }
}
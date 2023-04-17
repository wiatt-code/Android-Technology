package com.wiatt.simpledemo

import com.wiatt.arouter.BaseApplication

class SimpleDemoApplication: BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }

    companion object {
        var mApplication: SimpleDemoApplication? = null
    }
}
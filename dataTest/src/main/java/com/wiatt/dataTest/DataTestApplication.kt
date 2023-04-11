package com.wiatt.dataTest

import com.wiatt.arouter.BaseApplication

class DataTestApplication: BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }

    companion object {
        var mApplication: DataTestApplication? = null
    }
}
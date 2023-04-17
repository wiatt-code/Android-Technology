package com.wiatt.audioVideo

import com.wiatt.arouter.BaseApplication

open class AudioVideoApplication: BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }

    companion object {
        var mApplication: AudioVideoApplication? = null
    }
}
package com.wiatt.audioVideo

import com.alibaba.android.arouter.facade.annotation.Route
import android.app.Activity
import android.os.Bundle

@Route(path = "/plugin/MainActivity")
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audiovideo_main)

        println("1111, from plugin!!!")
    }
}
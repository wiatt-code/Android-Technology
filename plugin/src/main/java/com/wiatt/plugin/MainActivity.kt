package com.wiatt.plugin

import com.alibaba.android.arouter.facade.annotation.Route
import android.app.Activity
import android.os.Bundle
import com.wiatt.plugin.R

@Route(path = "/plugin/MainActivity")
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin_main)

        println("1111, from plugin!!!")
    }
}
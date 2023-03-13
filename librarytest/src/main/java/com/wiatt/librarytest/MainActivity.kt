package com.wiatt.librarytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.wiatt.net.HttpTool
import com.wiatt.net.ResultCallback

@Route(path = "/libraryTest/MainActivity")
class MainActivity : AppCompatActivity() {

    private lateinit var tvResuolt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResuolt = findViewById(R.id.tvResult)
    }

    fun doGet(view: View) {
        HttpTool.doGet(object : ResultCallback() {
            override fun onFail(failMsg: String) {

            }

            override fun onSucess(result: String) {
                runOnUiThread {
                    tvResuolt.text = result
                }
            }

        })
    }
}


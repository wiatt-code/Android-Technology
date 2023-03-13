package com.wiatt.custom_view.customLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wiatt.custom_view.R

class CircleActivity : AppCompatActivity() {

    lateinit var circleView: CircleView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle)
        circleView = findViewById(R.id.cv)
        println("getWidth， onCreate, = ${circleView.width}")
    }

    override fun onStart() {
        super.onStart()
        println("getWidth， onStart, = ${circleView.width}")
    }

    override fun onResume() {
        super.onResume()
        println("getWidth， onResume, = ${circleView.width}")
    }

    override fun onStop() {
        super.onStop()
        println("getWidth， onStop, = ${circleView.width}")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("getWidth， onDestroy, = ${circleView.width}")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        println("getWidth， onAttachedToWindow, = ${circleView.width}")
    }
}
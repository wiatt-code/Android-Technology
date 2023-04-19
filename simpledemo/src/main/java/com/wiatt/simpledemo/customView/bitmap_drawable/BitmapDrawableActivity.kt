package com.wiatt.simpledemo.customView.bitmap_drawable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wiatt.simpledemo.R

class BitmapDrawableActivity : AppCompatActivity() {
    lateinit var imageView: com.wiatt.simpledemo.customView.bitmap_drawable.DrawableView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitmap_drawable)

        imageView = findViewById(R.id.iv)
    }
}
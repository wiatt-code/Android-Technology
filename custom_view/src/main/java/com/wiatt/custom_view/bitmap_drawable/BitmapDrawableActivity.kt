package com.wiatt.custom_view.bitmap_drawable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wiatt.custom_view.R

class BitmapDrawableActivity : AppCompatActivity() {
    lateinit var imageView: DrawableView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitmap_drawable)

        imageView = findViewById(R.id.iv)
    }
}
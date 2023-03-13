package com.wiatt.custom_view.customTouch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.wiatt.custom_view.R

class TouchActivity : AppCompatActivity() {

    lateinit var view: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch)
        view = findViewById(R.id.view)
//        view.setOnClickListener {
//            Toast.makeText(this@TouchActivity, "点击了", Toast.LENGTH_SHORT).show()
//        }
//
//        view.setOnTouchListener(object: View.OnTouchListener{
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                Toast.makeText(this@TouchActivity, "点击了", Toast.LENGTH_SHORT).show()
//                return true
//            }
//        })
    }
}
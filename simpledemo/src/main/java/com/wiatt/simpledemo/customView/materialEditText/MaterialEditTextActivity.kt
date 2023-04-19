package com.wiatt.simpledemo.customView.materialEditText

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.postDelayed
import com.wiatt.simpledemo.R

class MaterialEditTextActivity : AppCompatActivity() {

    lateinit var materialEditText: MaterialEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_edit_text)

        materialEditText = findViewById(R.id.met)
        materialEditText.postDelayed(3000) {
            materialEditText.useFloatingLabel = true
        }
    }
}
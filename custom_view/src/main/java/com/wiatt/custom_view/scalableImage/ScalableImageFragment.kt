package com.wiatt.custom_view.scalableImage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wiatt.custom_view.R
import com.wiatt.custom_view.position_and_size.CusViewFragment

class ScalableImageFragment private constructor() : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scalable_image, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ScalableImageFragment()
    }
}
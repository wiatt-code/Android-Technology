package com.wiatt.custom_view.animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.wiatt.custom_view.R

class AnimationFragment private constructor() : Fragment() {

    lateinit var btnStartAnimation: Button
    lateinit var ptv: PageTurnView
    lateinit var pv: ProvinceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_animation, container, false)
        btnStartAnimation = rootView.findViewById(R.id.btnStartAnimation)
        ptv = rootView.findViewById(R.id.ptv)
        pv = rootView.findViewById(R.id.pv)

        val bottomFlipHoler1 = PropertyValuesHolder
            .ofFloat("bottomFlip", 0f, 60f)
        val flipRotationHolder1 = PropertyValuesHolder
            .ofFloat("flipRotation", 0f, 270f)
        val topFilpHolder1 = PropertyValuesHolder
            .ofFloat("topFlip", 0f, -60f)
        val animationPtv1 = ObjectAnimator.ofPropertyValuesHolder(ptv,
            bottomFlipHoler1, flipRotationHolder1, topFilpHolder1)
        animationPtv1.duration = 5000

        val bottomFlipHoler2 = PropertyValuesHolder
            .ofFloat("bottomFlip", 60f, 0f)
        val flipRotationHolder2 = PropertyValuesHolder
            .ofFloat("flipRotation", 270f, 0f)
        val topFilpHolder2 = PropertyValuesHolder
            .ofFloat("topFlip", -60f, 0f)
        val animationPtv2 = ObjectAnimator.ofPropertyValuesHolder(ptv,
            bottomFlipHoler2, flipRotationHolder2, topFilpHolder2)
        animationPtv2.duration = 5000
        val animaterSet = AnimatorSet()
        animaterSet.playSequentially(animationPtv1, animationPtv2)
        animaterSet.startDelay = 500
        animaterSet.start()

        val animationPv = ObjectAnimator.ofObject(pv,
            "province", ProvinceEvaluator(), "澳门特别行政区")
        animationPv.startDelay = 500
        animationPv.duration = 10000
        animationPv.start()

        btnStartAnimation.setOnClickListener {
            animaterSet.start()
            animationPv.start()
        }
        return rootView
    }

    companion object {

        @JvmStatic
        fun newInstance() = AnimationFragment()
    }
}
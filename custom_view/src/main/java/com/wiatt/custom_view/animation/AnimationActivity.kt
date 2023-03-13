package com.wiatt.custom_view.animation

import android.animation.*
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.wiatt.common.dp
import com.wiatt.custom_view.R

class AnimationActivity : AppCompatActivity() {

    lateinit var view: ProvinceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        view = findViewById(R.id.view)

        // ViewPropertyAnimator
        /*view.animate()
                .translationX(200.dp)
                .translationY(100.dp)
                .rotation(180f)
                .alpha(0.5f)
                .scaleX(2f)
                .scaleY(2f)
                .setDuration(50000)
                .startDelay = 1000*/

        // ObjectAnimator
        /*val animator = ObjectAnimator
                .ofFloat(view, "radius", 150.dp)
        animator.startDelay = 1000
        animator.start()*/

        // AnimatorSet
        /*val bottomFilpAnimator = ObjectAnimator.ofFloat(view,
                "bottomFlip", 60f)
        bottomFilpAnimator.startDelay = 200
        bottomFilpAnimator.duration = 1000
        val flipRotationAnimator = ObjectAnimator.ofFloat(view,
                "flipRotation", 270f)
        flipRotationAnimator.startDelay = 200
        flipRotationAnimator.duration = 1000
        val topFilpAnimator = ObjectAnimator.ofFloat(view,
                "topFlip", -60f)
        topFilpAnimator.startDelay = 200
        topFilpAnimator.duration = 1000
        val animaterSet = AnimatorSet()
        animaterSet.playSequentially(bottomFilpAnimator, flipRotationAnimator, topFilpAnimator)
        animaterSet.startDelay = 1000
        animaterSet.start()*/

        // PropertyValuesHolder
        /*val bottomFlipHoler = PropertyValuesHolder
                .ofFloat("bottomFlip", 60f)
        val flipRotationHolder = PropertyValuesHolder
                .ofFloat("flipRotation", 270f)
        val topFilpHolder = PropertyValuesHolder
                .ofFloat("topFlip", -60f)
        val holderAnimator = ObjectAnimator.ofPropertyValuesHolder(view,
                bottomFlipHoler, flipRotationHolder, topFilpHolder)
        holderAnimator.startDelay = 1000
        holderAnimator.duration = 2000
        holderAnimator.start()*/

        // keyFrame
        /*val length = 200.dp
        val keyframe1 = Keyframe.ofFloat(0f, 0f * length)
        val keyframe2 = Keyframe.ofFloat(0.2f, 1.5f * length)
        val keyframe3 = Keyframe.ofFloat(0.8f, 0.6f * length)
        val keyframe4 = Keyframe.ofFloat(1.0f, 1.0f * length)
        val keyframeHolder = PropertyValuesHolder
                .ofKeyframe("translationX",
                        keyframe1, keyframe2, keyframe3, keyframe4)
        val animator = ObjectAnimator
                .ofPropertyValuesHolder(view, keyframeHolder)
        animator.startDelay = 1000
        animator.duration = 2000
        animator.start()*/

        // Interpolator
       /*val animator = ObjectAnimator
                .ofFloat(view, "translationX", 200.dp)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.startDelay = 1000
        animator.duration = 2000
        animator.start()*/

        // TypeEvaluator
        /*val animator = ObjectAnimator.ofObject(view,
                "point", PointFEvaluator(), PointF(100.dp, 200.dp))
        animator.startDelay = 1000
        animator.duration = 2000
        animator.start()*/

        val animator = ObjectAnimator.ofObject(view,
                "province", ProvinceEvaluator(), "澳门特别行政区")
        animator.startDelay = 1000
        animator.duration = 10000
        animator.start()
    }
}
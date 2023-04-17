package com.wiatt.audioVideo

import com.alibaba.android.arouter.facade.annotation.Route
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.wiatt.common.base.BaseActivity

@Route(path = "/plugin/MainActivity")
class MainActivity : BaseActivity() {

    private var currentFragment: Fragment? = null
    private var audioVideoFragment: AudioVideoFragment = AudioVideoFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audiovideo_main)
        switchFragment(audioVideoFragment)
    }

    /**
     * 用于切换Fragment
     * 当不显示fragment时，nextFragment为空
     */
    private fun switchFragment(nextFragment: Fragment?) {
        if(nextFragment == currentFragment) {
            return
        }

        if (currentFragment != null && currentFragment!!.isAdded) {
            hideFragment(currentFragment!!)
        }

        if (nextFragment != null) {
            if (nextFragment.isAdded) {
                showFragment(nextFragment)
            } else {
                addFragment(nextFragment, R.id.flContainer)
            }
        }
        currentFragment = nextFragment
    }
}
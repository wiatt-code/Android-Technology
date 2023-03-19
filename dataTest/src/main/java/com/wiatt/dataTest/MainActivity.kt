package com.wiatt.dataTest

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.wiatt.dataTest.DataTest.DataTestFragment
import com.wiatt.dataTest.data.Repo
import com.wiatt.dataTest.data.TestApi
import com.wiatt.dataTest.base.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Route(path = "/dataTest/MainActivity")
class MainActivity : BaseActivity() {

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switchFragment(DataTestFragment.newInstance())
    }

    /**
     * 用于切换Fragment
     * 当不显示fragment时，nextFragment为空
     */
    fun switchFragment(nextFragment: Fragment?) {
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
                addFragment(nextFragment, R.id.llContainer)
            }
        }
        currentFragment = nextFragment
    }
}


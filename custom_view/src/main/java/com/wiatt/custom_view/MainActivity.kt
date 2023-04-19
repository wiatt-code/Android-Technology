package com.wiatt.custom_view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.wiatt.common.base.BaseActivity

@Route(path = "/customView/MainActivity")
open class MainActivity : BaseActivity() {

    private var currentFragment: Fragment? = null
    private var customViewFragment: CustomViewFragment = CustomViewFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customview_main)
        switchFragment(customViewFragment)
    }

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
                addFragment(nextFragment, R.id.flViewContainer)
            }
        }
        currentFragment = nextFragment
    }

    fun initData(){
//        infos.add(ViewInfo("dashboard go", DashboardActivity()))
//        infos.add(ViewInfo("pie go", PieActivity()))
//        infos.add(ViewInfo("avatarView go", AvatarActivity()))
//        infos.add(ViewInfo("xfermode go", XfermodeActivity()))
//        infos.add(ViewInfo("word measure go", WordMeasureActivity()))
//        infos.add(ViewInfo("Multiline text go", MultilineTextActivity()))
//        infos.add(ViewInfo("trans go", TransActivity()))
//        infos.add(ViewInfo("clip go", ClipActivity()))
//        infos.add(ViewInfo("page turn go", PageTurnActivity()))
//        infos.add(ViewInfo("animation go", AnimationActivity()))
//        infos.add(ViewInfo("bitmap&drawable go", BitmapDrawableActivity()))
//        infos.add(ViewInfo("materialEditText go", MaterialEditTextActivity()))
//        infos.add(ViewInfo("squareImage go", SquareImageActivity()))
//        infos.add(ViewInfo("circleView go", CircleActivity()))
    }
}
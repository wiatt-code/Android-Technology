package com.wiatt.manuscript

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var tlNavigation: TabLayout
    private lateinit var vpContent: ViewPager2
    private val modules: MutableList<ModuleInfo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tlNavigation = findViewById(R.id.tlNavigation)
        vpContent = findViewById(R.id.vpContent)
        updateViewPage2Sensitivity(vpContent)
        initData()
        initPage()
        TabLayoutMediator(tlNavigation, vpContent) { tab, position ->
            initTab(tab, modules[position])
        }.attach()
    }
    private fun initData() {
        val fragment =
        modules.add(
            ModuleInfo(
                "view",
                this.getDrawable(R.drawable.selector_tab_home)!!,
                ARouter.getInstance().build("/customView/CustomViewFragment").navigation() as Fragment
            )
        )
        modules.add(
            ModuleInfo(
                "data",
                this.getDrawable(R.drawable.selector_tab_home)!!,
                ARouter.getInstance().build("/dataTest/DataTestFragment").navigation() as Fragment
            )
        )
        modules.add(
            ModuleInfo(
                "video",
                this.getDrawable(R.drawable.selector_tab_home)!!,
                ARouter.getInstance().build("/audioVideo/AudioVideoFragment").navigation() as Fragment
            )
        )
    }

    private fun initTab(tab: TabLayout.Tab, module: ModuleInfo) {
        val itemView = View.inflate(this, R.layout.tablayout_item, null)
        val ivTabIcon = itemView.findViewById<ImageView>(R.id.ivTabIcon)
        val tvTabDescription = itemView.findViewById<TextView>(R.id.tvTabDescription)
        ivTabIcon.setImageDrawable(module.icon)
        tvTabDescription.text = module.name
        tab.customView = itemView
    }

    private fun initPage() {
        val fragments = mutableListOf<Fragment>()
        for (module in modules) {
            fragments.add(module.fragment)
        }
        val myFragmentPageAdapter = MyFragmentPageAdapter(
            supportFragmentManager,
            lifecycle,
            fragments
        )
        vpContent.adapter = myFragmentPageAdapter
    }

    /**
     * 更新viewPage的滑动灵敏度
     */
    private fun updateViewPage2Sensitivity(mViewPage: ViewPager2) {
        try {
            val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
            recyclerViewField.isAccessible = true

            val recyclerView = recyclerViewField[mViewPage] as RecyclerView

            val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
            touchSlopField.isAccessible = true

            val touchSlop = touchSlopField[recyclerView] as Int
            touchSlopField[recyclerView] = touchSlop * 4 //6 is empirical value
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
}
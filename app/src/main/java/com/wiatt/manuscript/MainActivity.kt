package com.wiatt.manuscript

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wiatt.custom_view.CustomViewFragment
import com.wiatt.dataTest.LibTestFragment
import com.wiatt.audioVideo.AudioVideoFragment

class MainActivity : AppCompatActivity() {

    private lateinit var tlNavigation: TabLayout
    private lateinit var vpContent: ViewPager2
    private val modules: MutableList<ModuleInfo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tlNavigation = findViewById(R.id.tlNavigation)
        vpContent = findViewById(R.id.vpContent)
        initData()
        initPage()
        TabLayoutMediator(tlNavigation, vpContent) { tab, position ->
            initTab(tab, modules[position])
        }.attach()
    }
    private fun initData() {
        modules.add(ModuleInfo("view",
            this.getDrawable(R.drawable.selector_tab_home)!!,
            CustomViewFragment.newInstance()))
        modules.add(ModuleInfo("data",
            this.getDrawable(R.drawable.selector_tab_home)!!,
            LibTestFragment.newInstance()))
        modules.add(ModuleInfo("video",
            this.getDrawable(R.drawable.selector_tab_home)!!,
            AudioVideoFragment.newInstance()))
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
        val myFragmentPageAdapter = MyFragmentPageAdapter(supportFragmentManager, lifecycle, fragments)
        vpContent.adapter = myFragmentPageAdapter
    }
}
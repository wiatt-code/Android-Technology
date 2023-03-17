package com.wiatt.simpledemo.myViewPager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.wiatt.simpledemo.R

class ViewPager2Activity : AppCompatActivity(), View.OnClickListener {
    lateinit var viewPager2: ViewPager2
    lateinit var lGroup: LinearLayout
    lateinit var iGroup: ImageView
    lateinit var lEmail: LinearLayout
    lateinit var iEmail: ImageView
    lateinit var lCar: LinearLayout
    lateinit var iCar: ImageView
    lateinit var lBike: LinearLayout
    lateinit var iBike: ImageView

    lateinit var iCurrent: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2)

        initPage()
        initTabView()
    }

    private fun initTabView() {
        lGroup = findViewById(R.id.tag_group)
        iGroup = findViewById(R.id.tag_group_draw)
        lEmail = findViewById(R.id.tag_email)
        iEmail = findViewById(R.id.tag_email_draw)
        lCar = findViewById(R.id.tag_car)
        iCar = findViewById(R.id.tag_car_draw)
        lBike = findViewById(R.id.tag_bike)
        iBike = findViewById(R.id.tag_bike_draw)

        lGroup.setOnClickListener(this)
        lEmail.setOnClickListener(this)
        lCar.setOnClickListener(this)
        lBike.setOnClickListener(this)

        iGroup.isSelected = true
        iCurrent = iGroup
    }

    private fun initPage() {
        viewPager2 = findViewById(R.id.vp2)
        val fragments = mutableListOf<Fragment>()
        fragments.add(BlankFragment.newInstance("分组"))
        fragments.add(BlankFragment.newInstance("邮箱"))
        fragments.add(BlankFragment.newInstance("汽车"))
        fragments.add(BlankFragment.newInstance("小电驴"))
        val myFragmentPageAdapter = MyFragmentPageAdapter(supportFragmentManager, lifecycle, fragments)
        viewPager2.adapter = myFragmentPageAdapter
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                changeTab(position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

    private fun changeTab(position: Int) {
        iCurrent.isSelected = false
        when(position) {
            R.id.tag_group -> {
                viewPager2.currentItem = 0
            }
            0 -> {
                iGroup.isSelected = true
                iCurrent = iGroup
            }
            R.id.tag_email -> {
                viewPager2.currentItem = 1
            }
            1 -> {
                iEmail.isSelected = true;
                iCurrent = iEmail;
            }
            R.id.tag_car -> {
                viewPager2.currentItem = 2
            }
            2 -> {
                iCar.isSelected = true
                iCurrent = iCar
            }
            R.id.tag_bike -> {
                viewPager2.currentItem = 3
            }
            3 -> {
                iBike.isSelected = true
                iCurrent = iBike
            }
        }
    }

    override fun onClick(v: View) {
        changeTab(v.id)
    }
}
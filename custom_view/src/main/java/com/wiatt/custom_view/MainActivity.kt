package com.wiatt.custom_view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.wiatt.custom_view.animation.AnimationActivity
import com.wiatt.custom_view.bitmap_drawable.BitmapDrawableActivity
import com.wiatt.custom_view.clip_and_trans.ClipActivity
import com.wiatt.custom_view.clip_and_trans.PageTurnActivity
import com.wiatt.custom_view.clip_and_trans.TransActivity
import com.wiatt.custom_view.customLayout.CircleActivity
import com.wiatt.custom_view.customLayout.SquareImageActivity
import com.wiatt.custom_view.materialEditText.MaterialEditTextActivity
import com.wiatt.custom_view.position_and_size.DashboardActivity
import com.wiatt.custom_view.position_and_size.PieActivity
import com.wiatt.custom_view.word_measure.MultilineTextActivity
import com.wiatt.custom_view.word_measure.WordMeasureActivity
import com.wiatt.custom_view.xfermode.AvatarActivity
import com.wiatt.custom_view.xfermode.XfermodeActivity

@Route(path = "/customView/MainActivity")
open class MainActivity : AppCompatActivity() {

    private lateinit var rvEntrance: RecyclerView
    private var infos = mutableListOf<EntranceInfo>()

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customview_main)
        println("1111, from custom view!!!")

        context = this
        rvEntrance = findViewById(R.id.rvEntrance)
        initData()
        var adapter = EntranceAdapter(this, infos)
        var layoutManager = LinearLayoutManager(context)
        rvEntrance.adapter = adapter
        rvEntrance.layoutManager = layoutManager

    }

    fun initData(){
        infos.add(EntranceInfo("dashboard go", DashboardActivity()))
        infos.add(EntranceInfo("pie go", PieActivity()))
        infos.add(EntranceInfo("avatarView go", AvatarActivity()))
        infos.add(EntranceInfo("xfermode go", XfermodeActivity()))
        infos.add(EntranceInfo("word measure go", WordMeasureActivity()))
        infos.add(EntranceInfo("Multiline text go", MultilineTextActivity()))
        infos.add(EntranceInfo("trans go", TransActivity()))
        infos.add(EntranceInfo("clip go", ClipActivity()))
        infos.add(EntranceInfo("page turn go", PageTurnActivity()))
        infos.add(EntranceInfo("animation go", AnimationActivity()))
        infos.add(EntranceInfo("bitmap&drawable go", BitmapDrawableActivity()))
        infos.add(EntranceInfo("materialEditText go", MaterialEditTextActivity()))
        infos.add(EntranceInfo("squareImage go", SquareImageActivity()))
        infos.add(EntranceInfo("circleView go", CircleActivity()))
    }
}
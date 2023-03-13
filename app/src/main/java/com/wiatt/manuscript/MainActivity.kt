package com.wiatt.manuscript

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.FileUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter

class MainActivity : AppCompatActivity() {

    private var infos: MutableList<ModuleInfo> = mutableListOf()
    lateinit var context: Context
    lateinit var rvEntrance: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        initData()
        rvEntrance = findViewById(R.id.rvEntrance)
        var adapter = ModuleAdapter(context, infos)
        var layoutManager = LinearLayoutManager(context)
        rvEntrance.adapter = adapter
        rvEntrance.layoutManager = layoutManager
    }
    fun initData() {
        infos.add(ModuleInfo("custom view go", "/customView/MainActivity"))
        infos.add(ModuleInfo("plugin go", "/plugin/MainActivity"))
        infos.add(ModuleInfo("simple demo go", "/simpledemo/MainActivity"))
        infos.add(ModuleInfo("library test go", "/libraryTest/MainActivity"))
    }
}
package com.wiatt.simpledemo.eventBusDemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.wiatt.simpledemo.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EventBusOneActivity : AppCompatActivity() {

    private lateinit var tvContent: TextView
    private lateinit var btnGoTwo: Button
    private lateinit var btnSendSticky: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        setContentView(R.layout.activity_event_bus_one)
        tvContent = findViewById(R.id.tvContent)
        btnGoTwo = findViewById(R.id.btnGoTwo)
        btnSendSticky = findViewById(R.id.btnSendSticky)

        btnGoTwo.setOnClickListener {
            startActivity(Intent(this, EventBusTwoActivity::class.java))
        }
        btnSendSticky.setOnClickListener {
            EventBus.getDefault().postSticky(StickyMsgEvent("粘性事件"))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    //处理普通事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMsgEvent(normalMsgEvent: NormalMsgEvent) {
        Log.i(TAG, "onMsgEvent：str = ${normalMsgEvent.content}")
        tvContent.text = normalMsgEvent.content
        Toast.makeText(this,
            "onMsgEvent: ${normalMsgEvent.content}",
            Toast.LENGTH_LONG)
            .show()
    }


    //处理函数执行线程与发布线程一致
    @Subscribe(threadMode = ThreadMode.POSTING)
    fun onMessageEventPosting(threadMsgEvent: ThreadMsgEvent) {
        Log.i(TAG, "str_1 = ${threadMsgEvent.content}, onMessageEventPosting：${Thread.currentThread().name}")
    }

    //处理函数执行线程为主线程
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEventMain(threadMsgEvent: ThreadMsgEvent) {
        Log.i(TAG, "str_2 = ${threadMsgEvent.content}, onMessageEventMain：${Thread.currentThread().name}")
    }

    //处理函数执行线程为主线程
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onMessageEventMainOrdered(threadMsgEvent: ThreadMsgEvent) {
        Log.i(TAG, "str_3 = ${threadMsgEvent.content}, onMessageEventMainOrdered：${Thread.currentThread().name}")
    }

    //处理函数执行线程为后台线程
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    fun onMessageEventBackground(threadMsgEvent: ThreadMsgEvent) {
        Log.i(TAG, "str_4 = ${threadMsgEvent.content}, onMessageEventBackground：${Thread.currentThread().name}")
    }

    //处理函数执行线程为后台线程
    @Subscribe(threadMode = ThreadMode.ASYNC)
    fun onMessageEventAsync(threadMsgEvent: ThreadMsgEvent) {
        Log.i(TAG, "str_5 = ${threadMsgEvent.content}, onMessageEventAsync：${Thread.currentThread().name}")
    }


    //订阅者的接收事件处理函数, 事件优先级0
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 0)
    fun onMessageEventPriority0(priorityMsgEvent: PriorityMsgEvent) {
        Log.i(TAG, "str_1 = ${priorityMsgEvent.content}, onMessageEvent：priority = 0 ")
    }

    //订阅者的接收事件处理函数, 事件优先级1
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    fun onMessageEventPriority1(priorityMsgEvent: PriorityMsgEvent) {
        Log.i(TAG, "str_2 = ${priorityMsgEvent.content}, onMessageEvent：priority = 1 ")
    }

    //订阅者的接收事件处理函数, 事件优先级2
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 2)
    fun onMessageEventPriority2(priorityMsgEvent: PriorityMsgEvent) {
        Log.i(TAG, "str_3 = ${priorityMsgEvent.content}, onMessageEvent：priority = 2 ")
    }

    companion object {
        const val TAG = "EventBus"
    }
}
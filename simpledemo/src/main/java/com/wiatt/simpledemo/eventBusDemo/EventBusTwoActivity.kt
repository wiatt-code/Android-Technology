package com.wiatt.simpledemo.eventBusDemo

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

class EventBusTwoActivity : AppCompatActivity() {

    private lateinit var tvStickyContent: TextView
    private lateinit var btnSendNormal: Button
    private lateinit var btnSendThread: Button
    private lateinit var btnSendPriority: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_bus_two)
        tvStickyContent = findViewById(R.id.tvStickyContent)
        btnSendNormal = findViewById(R.id.btnSendNormal)
        btnSendThread = findViewById(R.id.btnSendThread)
        btnSendPriority = findViewById(R.id.btnSendPriority)
        EventBus.getDefault().register(this)

        btnSendNormal.setOnClickListener {
            EventBus.getDefault().post(NormalMsgEvent("普通事件"))
        }
        btnSendThread.setOnClickListener {
            EventBus.getDefault().post(ThreadMsgEvent("线程测试事件"))
        }
        btnSendPriority.setOnClickListener {
            EventBus.getDefault().post(PriorityMsgEvent("优先级测试事件"))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onStickyMsgEvent(stickyMsgEvent: StickyMsgEvent) {
        Log.i(TAG, "onStickyMsgEvent：str = ${stickyMsgEvent.content}")
        tvStickyContent.text = stickyMsgEvent.content
        Toast.makeText(this,
            "onMsgEvent: ${stickyMsgEvent.content}",
            Toast.LENGTH_LONG)
            .show()
        EventBus.getDefault().removeStickyEvent(stickyMsgEvent)
    }

    companion object {
        const val TAG = "EventBus"
    }
}
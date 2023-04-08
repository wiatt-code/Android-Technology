package com.wiatt.common

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

open abstract class WeakHandler<T>(looper: Looper, owner: T) : Handler(looper) {
    private var mOwner: WeakReference<T> = WeakReference(owner)

    /**
     * 如果handler所在的activity或者fragment已经销毁，则不再继续处理handler消息
     */
    override fun handleMessage(msg: Message) {
        if (isInvalid()) {
            msg.what  = -1
        }
    }

    fun getOwner(): T? {
        return mOwner.get()
    }

    private fun isInvalid(): Boolean {
        return when {
            mOwner.get() is Activity -> {
                (mOwner.get() as Activity).isDestroyed
            }
            mOwner.get() is Fragment -> {
                (mOwner.get() as Fragment).isVisible
            }
            else -> {
                false
            }
        }
    }

    /**
     * 用于发送handler消息
     * @param what：消息标识
     * @param obj: obj == null, 表示没有使用obj
     * @param arg1: arg1 == 0, 表示没有使用arg1
     * @param arg2: arg2 == 0, 表示没有使用arg2
     * @param delay: delay == 0, 表示没有使用延时机制
     */
    fun sendHandlerMessage(what: Int, obj: Any?, arg1: Int, arg2: Int, delay: Long) {
        val msg = obtainMessage(what)
        if (obj != null) {
            msg.obj = obj
        }
        if (arg1 != 0) {
            msg.arg1 = arg1
        }
        if (arg2 != 0) {
            msg.arg2 = arg2
        }
        if (delay != 0L) {
            sendMessageDelayed(msg, delay)
        } else {
            sendMessage(msg)
        }
    }
}
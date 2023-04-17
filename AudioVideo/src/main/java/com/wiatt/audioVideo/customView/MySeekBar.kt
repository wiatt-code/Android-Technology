package com.wiatt.audioVideo.customView

import android.content.Context
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import com.wiatt.common.LogUtil
import com.wiatt.common.WeakHandler


class MySeekBar(context: Context, attrs: AttributeSet?):
    AppCompatSeekBar(context, attrs) {

    private var sbHandler: SbHandler = SbHandler(Looper.getMainLooper(), this)
    private var listener: OnMySeekBarChangeListener? = null
    var totalTime = 0
    var curTime = 0
    // 是否处于手势滑动的状态
    private var isGesture: Boolean = false

    init {
        initParams()
    }

    private fun initParams() {
        this.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // isFromUser：progress改变是否来自用户操作
                // 手势滑动导致的progresss也属于来自用户操作
                var isFromUser = fromUser
                if (isGesture) {
                    isFromUser = true
                }
                if (isFromUser) {
                    curTime = ((progress.toFloat() / 100) * totalTime).toInt()
                }
                listener?.onProgressChanged(this@MySeekBar, isFromUser, curTime, totalTime)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                LogUtil.i(TAG, "onStartTrackingTouch, curTime = $curTime, totalTime = $totalTime")
                sbHandler.removeMessages(MSG_AUTO)
                listener?.onStartTrackingTouch(this@MySeekBar, curTime, totalTime)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                LogUtil.i(TAG, "onStopTrackingTouch, curTime = $curTime, totalTime = $totalTime")
                listener?.onStopTrackingTouch(this@MySeekBar, curTime, totalTime)

                sbHandler.removeMessages(MSG_AUTO)
                sbHandler.sendHandlerMessage(MSG_AUTO, null, 0, 0, (AUTO_MOVE_INTERVAL * 1000).toLong())
            }
        })
    }

    fun setOnMySeekBarChangeListener(listener: OnMySeekBarChangeListener) {
        this.listener = listener
    }

    /**
     * 显示seekbar
     */
    fun showSeekBar(totalTime: Int) {
        this.totalTime = totalTime
        sbHandler.removeMessages(MSG_AUTO)
        sbHandler.sendHandlerMessage(MSG_AUTO, null, 0, 0, (AUTO_MOVE_INTERVAL * 1000).toLong())
    }

    /**
     * 隐藏seekbar
     */
    fun hideSeekBar() {
        sbHandler.removeMessages(MSG_AUTO)
    }

    /**
     * 自动移动seekbar
     */
    fun autoMoveSeekBar() {

        listener?.apply {
            curTime = this.onGetCurTime()
            LogUtil.i(TAG, "curTime = $curTime, totalTime = $totalTime")
            if (totalTime <= 0) {
                this@MySeekBar.progress = 0
            } else {
                var usedProgress = ((curTime.toFloat() / totalTime.toFloat()) * 100).toInt()
                this@MySeekBar.progress = usedProgress
            }
        }
        sbHandler.removeMessages(MSG_AUTO)
        sbHandler.sendHandlerMessage(MSG_AUTO, null, 0, 0, (AUTO_MOVE_INTERVAL * 1000).toLong())
    }

    /**
     * 手势滑动，移动seekbar
     */
    fun gestureMoveSeekBar(changeProgress: Int) {
        if (!isGesture) {
            isGesture = true
            sbHandler.removeMessages(MSG_AUTO)
            listener?.onStartTrackingTouch(this@MySeekBar, curTime, totalTime)
        }
        progress += changeProgress
        sbHandler.removeMessages(MSG_GESTURE_FINISH)
        sbHandler.sendHandlerMessage(MSG_GESTURE_FINISH, null, 0, 0, (GESTURE_FINISH_INTERVAL * 1000).toLong())
    }

    class SbHandler(looper: Looper, owner: MySeekBar): WeakHandler<MySeekBar>(looper, owner) {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what) {
                MSG_AUTO -> {
                    LogUtil.i(TAG, "has MSG_AUTO")
                    getOwner()?.autoMoveSeekBar()
                }
                MSG_GESTURE_FINISH -> {
                    getOwner()?.let {
                        it.isGesture = false
                        it.sbHandler.removeMessages(MSG_AUTO)
                        it.sbHandler.sendHandlerMessage(MSG_AUTO, null, 0, 0, (AUTO_MOVE_INTERVAL * 1000).toLong())
                        it.listener?.onStopTrackingTouch(it, it.curTime, it.totalTime)
                    }
                }
                else -> { }
            }
        }
    }

    interface OnMySeekBarChangeListener {

        /**
         * 获取播放进度信息
         * @return curVideoTime: 当前播放时间
         * curVideoTime：当前播放时间
         */
        fun onGetCurTime(): Int

        /**
         * 播放进度改变
         * @param curTime: 当前时间
         * @param totalTime: 总时间
         */
        fun onProgressChanged(seekBar: MySeekBar, fromUser: Boolean, curTime: Int, totalTime: Int)


        /**
         * 开始手动滑动进度条
         */
        fun onStartTrackingTouch(seekBar: MySeekBar, curTime: Int, totalTime: Int)

        /**
         * 停止手动滑动进度条
         */
        fun onStopTrackingTouch(seekBar: MySeekBar, curTime: Int, totalTime: Int)
    }

    companion object {
        val TAG = MySeekBar::class.java.simpleName
        const val MSG_AUTO = 1101
        const val MSG_GESTURE_FINISH = 1102

        const val AUTO_MOVE_INTERVAL = 1
        const val GESTURE_FINISH_INTERVAL = 1
    }
}
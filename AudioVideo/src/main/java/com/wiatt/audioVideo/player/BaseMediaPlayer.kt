package com.wiatt.audioVideo.player

import android.content.Context
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import com.wiatt.common.WeakHandler

/**
 * 主要用于将播放器指令放入handler消息队列中排序，再依次取出执行，
 * 这样就能保证指令的执行顺序不会错乱
 */
abstract class BaseMediaPlayer(open var context: Context) {

    private var mMpThread: HandlerThread = HandlerThread("MpThread")
    private var mMpHandler: MpHandler

    protected var mPlayerState: PlayerState = PlayerState.IDLE
    var mPlayUrl: String? = null

    init {
        mMpThread.start()
        mMpHandler = MpHandler(mMpThread.looper, this)
    }


    /**
     * 判断是否处于可操作的播放状态。
     * 具体来说是：可以停止播放、可以调整播放位置
     */
    protected fun baseIsInPlayState(): Boolean {
        return mPlayerState == PlayerState.PREPARED ||
                mPlayerState == PlayerState.PLAYING ||
                mPlayerState == PlayerState.PAUSED ||
                mPlayerState == PlayerState.ERROR
    }

    fun getPlayUrl(): String? {
        return mPlayUrl
    }

    protected fun baseReset() {
        mMpHandler.apply {
            this.sendHandlerMessage(MSG_RESET, null, 0, 0, 0)
        }
    }

    protected fun baseSetDataSource(dataSource: String) {
        if (TextUtils.isEmpty(dataSource)) {
            return
        }
        mPlayUrl = dataSource
        mMpHandler.apply {
            this.removeMessages(MSG_SET_DATA_SOURCE)
            this.removeMessages(MSG_PREPARE_ASYNC)
            this.sendHandlerMessage(MSG_SET_DATA_SOURCE, dataSource, 0, 0, 0)
        }
    }

    protected fun basePrepareAsync() {
        mMpHandler.apply {
            this.removeMessages(MSG_PREPARE_ASYNC)
            this.sendHandlerMessage(MSG_PREPARE_ASYNC, null, 0, 0, 0)
        }
    }

    protected fun baseStart() {
        mMpHandler.apply {
            this.removeMessages(MSG_START)
            this.sendHandlerMessage(MSG_START, null, 0, 0, 0)
        }
    }

    protected fun basePause() {
        mMpHandler.apply {
            this.removeMessages(MSG_PAUSE)
            this.sendHandlerMessage(MSG_PAUSE, null, 0, 0, 0)
        }
    }

    protected fun baseSeekTo(pos: Int) {
        mMpHandler.apply {
            this.removeMessages(MSG_SEEK)
            this.sendHandlerMessage(MSG_SEEK, null, pos, 0, 0)
        }
    }

    protected fun baseStop() {
        mMpHandler.apply {
            this.removeMessages(MSG_STOP)
            this.sendHandlerMessage(MSG_STOP, null, 0, 0, 0)
        }
    }

    protected fun baseRelease() {
        mMpHandler.apply {
            this.removeCallbacksAndMessages(null)
        }
    }

    class MpHandler(looper: Looper, baseMediaPlayer: BaseMediaPlayer): WeakHandler<BaseMediaPlayer>(looper, baseMediaPlayer) {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what) {
                MSG_RESET -> {
                    getOwner()?.dealWithReset()
                }
                MSG_SET_DATA_SOURCE -> {
                    getOwner()?.dealWithSetDataSource(msg.obj as String)
                }
                MSG_PREPARE_ASYNC -> {
                    getOwner()?.dealWithPrepareAsync()
                }
                MSG_START -> {
                    getOwner()?.dealWithStart()
                }
                MSG_PAUSE -> {
                    getOwner()?.dealWithPause()
                }
                MSG_SEEK -> {
                    getOwner()?.dealWithSeek(msg.arg1)
                }
                MSG_STOP -> {
                    getOwner()?.dealWithStop()
                }
            }
        }
    }

    protected abstract fun dealWithReset()

    protected abstract fun dealWithSetDataSource(dataSource: String)

    protected abstract fun dealWithPrepareAsync()

    protected abstract fun dealWithStart()

    protected abstract fun dealWithPause()

    protected abstract fun dealWithSeek(pos: Int)

    protected abstract fun dealWithStop()

    companion object {
        const val MSG_RESET = 0
        const val MSG_SET_DATA_SOURCE = 1
        const val MSG_PREPARE_ASYNC = 2
        const val MSG_START = 3
        const val MSG_PAUSE = 4
        const val MSG_SEEK = 5
        const val MSG_STOP = 6

    }
}
package com.wiatt.audioVideo.player

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.view.SurfaceHolder
import androidx.annotation.RequiresApi
import com.wiatt.audioVideo.eventBusBean.EventPlayerError
import com.wiatt.audioVideo.eventBusBean.EventPlayerState
import com.wiatt.audioVideo.eventBusBean.EventPlayerVideoSize
import com.wiatt.common.LogUtil
import org.greenrobot.eventbus.EventBus
import java.lang.Exception

class AndroidMediaPlayer(override var context: Context):
    BaseMediaPlayer(context), Player,
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnBufferingUpdateListener,
    MediaPlayer.OnSeekCompleteListener,
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnVideoSizeChangedListener,
    MediaPlayer.OnErrorListener,
    MediaPlayer.OnInfoListener {

    var mediaPlayer: MediaPlayer? = null

    init {
        initPlayer()
    }

    private fun initPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setScreenOnWhilePlaying(true)
            initListener()
            setPlayerState(PlayerState.IDLE)
        }
    }

    private fun setPlayerState(playerState: PlayerState) {
        LogUtil.i(TAG, "oldPlayerState = $mPlayerState, newPlayerState = $playerState")
        EventBus.getDefault().post(EventPlayerState(this, playerState, mPlayerState))
        mPlayerState = playerState
    }

    private fun initListener() {
        mediaPlayer?.let {
            it.setOnPreparedListener(this)
            it.setOnBufferingUpdateListener(this)
            it.setOnSeekCompleteListener(this)
            it.setOnCompletionListener(this)
            it.setOnVideoSizeChangedListener(this)
            it.setOnErrorListener(this)
            it.setOnInfoListener(this)
        }
    }

    private fun isInPlayState(): Boolean {
        return mediaPlayer != null && baseIsInPlayState()
    }

    /**
     * 必须先调用该方法，设置播放窗口
     */
    override fun setDisplay(surfaceHolder: SurfaceHolder) {
        mediaPlayer?.setDisplay(surfaceHolder)
    }

    override fun reset() {
        baseReset()
    }

    override fun setDataSource(dataSource: String) {
        baseSetDataSource(dataSource)
    }

    override fun prepare() {
        // 暂未使用同步准备功能
    }

    override fun prepareAsync() {
        basePrepareAsync()
    }

    override fun start() {
        baseStart()
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer != null
                && mediaPlayer!!.isPlaying
                && mPlayerState == PlayerState.PLAYING
    }

    override fun pause() {
        basePause()
    }

    override fun isPause(): Boolean {
        return mediaPlayer != null && mPlayerState == PlayerState.PAUSED
    }

    override fun stop() {
        if (mPlayerState != PlayerState.STOPPED) {
            baseStop()
        }
    }

    override fun seekTo(pos: Int) {
        baseSeekTo(pos)
    }

    override fun isSeeking(): Boolean {
        return mediaPlayer != null && mPlayerState == PlayerState.SEEKING
    }

    /**
     * release操作只能在页面关闭的时候执行，
     * 释放mediaPlayer的同时，应回收AndroidMediaPlayer实例
     * 否则应调用reset()方法，然后复用mediaPlayer播放器
     */
    override fun release() {
        baseRelease()
        mediaPlayer?.let {
            it.release()
            mediaPlayer = null
        }
    }

    override fun getPlayState(): PlayerState {
        return mPlayerState
    }

    override fun getRate(): Float {
        return 1.0f
    }

    override fun setRate(rate: Float) {
        // 暂未使用
    }

    override fun getDuration(): Int {
        return if (isInPlayState()) {
            mediaPlayer!!.duration / 1000
        } else {
            -1
        }
    }

    override fun getCurrentPosition(): Int {
        return if (isInPlayState()) {
            mediaPlayer!!.currentPosition / 1000
        } else {
            -1
        }
    }



    override fun dealWithReset() {
        mediaPlayer?.let {
            try {
                if (mPlayerState != PlayerState.IDLE) {
                    it.reset()
                    setPlayerState(PlayerState.IDLE)
                }
            } catch (e: Exception) {
                LogUtil.e(TAG, "dealWithReset Exception: e = ${e.message}")
                e.printStackTrace()
                reset()
                EventBus.getDefault().post(EventPlayerError(this, PlayerError.ERROR_RESET_CRASH))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun dealWithSetDataSource(dataSource: String) {
        mediaPlayer?.let {
            try {
                if (mPlayerState == PlayerState.IDLE) {
                    it.setDataSource(dataSource)
//                    it.setDataSource(context.assets.openFd("video/big_buck_bunny.mp4"))
                    setPlayerState(PlayerState.INITIALIZED)
                }
            } catch (e: Exception) {
                LogUtil.e(TAG, "dealWithSetDataSource Exception: e = ${e.message}")
                e.printStackTrace()
                reset()
                EventBus.getDefault().post(EventPlayerError(this, PlayerError.ERROR_SET_SOURCE_CRASH))
            }
        }
    }

    override fun dealWithPrepareAsync() {
        mediaPlayer?.let {
            try {
                if (mPlayerState == PlayerState.INITIALIZED) {
                    it.prepareAsync()
                    // 不再这里修改状态，prepare操作完成会产生回调消息
                }
            } catch (e: Exception) {
                LogUtil.e(TAG, "dealWithPrepareAsync Exception: e = ${e.message}")
                e.printStackTrace()
                reset()
                EventBus.getDefault().post(EventPlayerError(this, PlayerError.ERROR_PREPARE_CRASH))
            }
        }
    }

    override fun dealWithStart() {
        mediaPlayer?.let {
            try {
                if (mPlayerState == PlayerState.PREPARED
                    || mPlayerState == PlayerState.PAUSED
                    || mPlayerState == PlayerState.SEEKING
                    || mPlayerState == PlayerState.COMPLETED) {
                    it.start()
                    setPlayerState(PlayerState.PLAYING)
                }
            } catch (e: Exception) {
                LogUtil.e(TAG, "dealWithStart Exception: e = ${e.message}")
                e.printStackTrace()
                reset()
                EventBus.getDefault().post(EventPlayerError(this, PlayerError.ERROR_START_CRASH))
            }
        }
    }

    override fun dealWithPause() {
        mediaPlayer?.let {
            try {
                if (mPlayerState == PlayerState.PLAYING) {
                    it.pause()
                    setPlayerState(PlayerState.PAUSED)
                }
            } catch (e: Exception) {
                LogUtil.e(TAG, "dealWithPause Exception: e = ${e.message}")
                e.printStackTrace()
                reset()
                EventBus.getDefault().post(EventPlayerError(this, PlayerError.ERROR_PAUSE_CRASH))
            }
        }
    }

    override fun dealWithSeek(pos: Int) {
        mediaPlayer?.let {
            try {
                if (isInPlayState()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        it.seekTo((pos * 1000).toLong(), MediaPlayer.SEEK_CLOSEST)
                    } else {
                        it.seekTo(pos * 1000)
                    }
                    setPlayerState(PlayerState.SEEKING)
                }
            } catch (e: Exception) {
                LogUtil.e(TAG, "dealWithSeek Exception: e = ${e.message}")
                e.printStackTrace()
                reset()
                EventBus.getDefault().post(EventPlayerError(this, PlayerError.ERROR_SEEK_CRASH))
            }
        }
    }

    override fun dealWithStop() {
        mediaPlayer?.let {
            try {
                if (isInPlayState()) {
                    it.stop()
                    setPlayerState(PlayerState.STOPPED)
                }
            } catch (e: Exception) {
                LogUtil.e(TAG, "dealWithStop Exception: e = ${e.message}")
                e.printStackTrace()
                reset()
                EventBus.getDefault().post(EventPlayerError(this, PlayerError.ERROR_STOP_CRASH))
            }
        }
    }



    /**
     * 监听回调，播放器是否准备就绪
     */
    override fun onPrepared(mp: MediaPlayer?) {
        setPlayerState(PlayerState.PREPARED)
    }

    /**
     * 视频缓冲进度，或者播放进度
     */
    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        // 暂不处理
        LogUtil.i(TAG, "percent = $percent")
    }

    /**
     * 监听回调，调整播放位置是否完成
     */
    override fun onSeekComplete(mp: MediaPlayer?) {
        setPlayerState(PlayerState.PLAYING)
    }

    /**
     * 监听回调，播放完成
     */
    override fun onCompletion(mp: MediaPlayer?) {
        setPlayerState(PlayerState.COMPLETED)
    }

    /**
     * 监听回调，播放窗口大小改变
     */
    override fun onVideoSizeChanged(mp: MediaPlayer?, width: Int, height: Int) {
        LogUtil.i(TAG, "onVideoSizeChanged: width = $width, height = $height")
        EventBus.getDefault().post(EventPlayerVideoSize(this, width, height))
    }

    /**
     * 监听回调，播放出错
     */
    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        LogUtil.e(TAG, "onError: what = $what, extra = $extra")
        reset()
        EventBus.getDefault().post(EventPlayerError(this, what))
        return true
    }

    /**
     * 监听回调，出现警告或者其他信息
     */
    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        //暂不处理
        LogUtil.w(TAG, "onInfo: what = $what, extra = $extra")
        return false
    }

    companion object {
        val TAG: String = AndroidMediaPlayer::class.java.simpleName
    }
}
package com.wiatt.audioVideo

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Looper
import android.os.Message
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.wiatt.audioVideo.eventBusBean.EventPlayerError
import com.wiatt.audioVideo.eventBusBean.EventPlayerState
import com.wiatt.audioVideo.eventBusBean.EventPlayerVideoSize
import com.wiatt.audioVideo.player.AndroidMediaPlayer
import com.wiatt.audioVideo.player.PlayerState
import com.wiatt.common.LogUtil
import com.wiatt.common.WeakHandler
import com.wiatt.common.base.BaseActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.math.abs

class VideoActivity: BaseActivity(), SurfaceHolder.Callback{

    private var mediaPlayer: AndroidMediaPlayer = AndroidMediaPlayer(this)
    private lateinit var sfVideo: SurfaceView

    private var currentFragment: Fragment? = null
    private var videoControlCallback: VideoControlCallback = VideoControlCallback()
    private var videoControlFragment: VideoControlFragment = VideoControlFragment.newInstance(videoControlCallback)
    private var brightVolumeFragment: BrightVolumeFragment = BrightVolumeFragment.newInstance()
    private var videoHandler: VideoHandler = VideoHandler(Looper.getMainLooper(), this)
    private var gestureListener = VideoGestureListener()
    private lateinit var gestureDetector: GestureDetectorCompat
    private var displayHeight: Int = 0  // 单位为px
    private var displayWidth: Int = 0  // 单位为px
    private var isRestart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        // androidx.core 要提高到1.5.0以上版本，才可以替换该api
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        LogUtil.i(TAG, "onCreate")
        isRestart = false
        setContentView(R.layout.activity_video)
        EventBus.getDefault().register(this)
        sfVideo = findViewById(R.id.sfVideo)

        gestureDetector = GestureDetectorCompat(this, gestureListener)

        switchVideoControlFragment(isShow = true, isTiming = true)
        var sfHolder = sfVideo.holder
        sfHolder.addCallback(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        LogUtil.i(TAG, "onNewIntent")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtil.i(TAG, "onRestart")
        isRestart = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.i(TAG, "onSaveInstanceState")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.i(TAG, "onStart")
        // 用于黑屏后重新点亮屏幕时，展示videoControlFragment
        switchVideoControlFragment(isShow = true, isTiming = true)
        if (mediaPlayer.isPlaying()) {
            videoControlFragment.updateIvSwitch(true)
        } else {
            videoControlFragment.updateIvSwitch(false)
        }

        videoControlFragment.setVideoName("trailer.mp4")

        displayHeight = resources.displayMetrics.heightPixels
        displayWidth = resources.displayMetrics.widthPixels
    }

    override fun onResume() {
        super.onResume()
        LogUtil.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.i(TAG, "onPause")
        switchBSFragment(isShow = false, isTiming = false)
        switchVideoControlFragment(isShow = false, isTiming = false)
    }

    // 在该方法中回收界面相关的资源，
    // 记录数据记录，
    // 在onRestart()方法中恢复
    override fun onStop() {
        super.onStop()
        LogUtil.i(TAG, "onStop")
        mediaPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i(TAG, "onDestroy")
        mediaPlayer.reset()
        mediaPlayer.release()
        videoHandler.removeCallbacksAndMessages(null)
        EventBus.getDefault().unregister(this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventPlayerError(eventPlayerError: EventPlayerError) {
        Toast.makeText(
            this,
            "播放器出现错误，错误代码：${eventPlayerError.errorType}",
            Toast.LENGTH_LONG
        ).show()
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onEventPlayerState(eventPlayerState: EventPlayerState) {
        when(eventPlayerState.newState) {
            PlayerState.IDLE -> {
                if (videoControlFragment.isVisible) {
                    videoControlFragment.updateIvSwitch(false)
                }
                mediaPlayer.setDataSource(videoUrl)
            }
            PlayerState.INITIALIZED -> {
                LogUtil.i(TAG, "PlayerState.INITIALIZED")
                mediaPlayer.prepareAsync()
            }
            PlayerState.PREPARED -> {
                switchVideoControlFragment(isShow = true, isTiming = true)
                videoControlFragment.setVideoTime(
                    mediaPlayer.getDuration(),
                    mediaPlayer.getCurrentPosition()
                )

                mediaPlayer.start()
            }
            PlayerState.PLAYING -> {
                if (videoControlFragment.isVisible) {
                    videoControlFragment.updateIvSwitch(true)
                }
            }
            PlayerState.PAUSED -> {
                if (videoControlFragment.isVisible) {
                    videoControlFragment.updateIvSwitch(false)
                }
            }
            PlayerState.STOPPED -> {
                if (videoControlFragment.isVisible) {
                    videoControlFragment.updateIvSwitch(false)
                }
            }
            PlayerState.COMPLETED -> {
                if (videoControlFragment.isVisible) {
                    videoControlFragment.updateIvSwitch(false)
                }
                mediaPlayer.start()
            }
            else -> {

            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventPlayerVideoSize(eventPlayerVideoSize: EventPlayerVideoSize) {

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        LogUtil.i(TAG, "surfaceCreated")
        holder.let {
            mediaPlayer.setDisplay(it)
            if (!isRestart) {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(videoUrl)
            }

        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    /**
     * 控制VideoControl的显示与隐藏
     * @param isShow: 是否显示fragment
     * @param isTiming：是否设置自动关闭定时器
     */
    private fun switchVideoControlFragment(isShow: Boolean, isTiming: Boolean){
        if(isShow) {
            if (!videoControlFragment.isVisible) {
                switchFragment(videoControlFragment)
            }
            videoHandler.removeMessages(MSG_HIDE_VCF)
            videoHandler.removeMessages(MSG_HIDE_BSF)
            if (isTiming) {
                videoHandler.sendHandlerMessage(MSG_HIDE_VCF, null, 0, 0, (HIDE_VCF_INTERVAL * 1000).toLong())
            }
        } else {
            switchFragment(null)
            videoHandler.removeMessages(MSG_HIDE_VCF)
        }
    }

    /**
     * 控制BrightVolumeFragment的显示与隐藏
     */
    private fun switchBSFragment(isShow: Boolean, isTiming: Boolean) {
        if(isShow) {
            if (!brightVolumeFragment.isVisible) {
                switchFragment(brightVolumeFragment)
            }
            videoHandler.removeMessages(MSG_HIDE_VCF)
            videoHandler.removeMessages(MSG_HIDE_BSF)
            if (isTiming) {
                videoHandler.sendHandlerMessage(MSG_HIDE_BSF, null, 0, 0, (HIDE_BSF_INTERVAL * 1000).toLong())
            }
        } else {
            switchFragment(null)
            videoHandler.removeMessages(MSG_HIDE_BSF)
        }
    }

    /**
     * 用于切换Fragment
     * 当不显示fragment时，nextFragment为空
     */
    private fun switchFragment(nextFragment: Fragment?) {
        if(nextFragment == currentFragment) {
            return
        }

        if (currentFragment != null && currentFragment!!.isAdded) {
            hideFragment(currentFragment!!)
        }

        if (nextFragment != null) {
            if (nextFragment.isAdded) {
                showFragment(nextFragment)
            } else {
                addFragment(nextFragment, R.id.clControl)
            }
        }
        currentFragment = nextFragment
    }

    /**
     * 根据播放器得当前状态，控制播放或者暂停
     */
    fun setPlayOrPause() {
        when(mediaPlayer.getPlayState()) {
            PlayerState.PLAYING -> {
                mediaPlayer.pause()
            }
            PlayerState.PAUSED -> {
                mediaPlayer.start()
            }
            PlayerState.STOPPED -> {
                mediaPlayer.prepareAsync()
            }
            PlayerState.IDLE -> {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(videoUrl)
            }
            else -> { }
        }
    }

    // VideoActivity与VideoControlFragment沟通的回调类
    inner class VideoControlCallback {
        fun onClickBack() {
            videoHandler.removeCallbacksAndMessages(null)
            finish()
        }

        fun onClickSwitch() {
            setPlayOrPause()
        }

        fun onGetVideoTotalTime(): Int {
            return mediaPlayer.getDuration()
        }

        fun onGetVideoCurTime(): Int{
            return mediaPlayer.getCurrentPosition()
        }

        fun onUpdateVideoProgress(curTime: Int) {
            mediaPlayer.seekTo(curTime)
        }

        fun onSwitchMySelf(isShow: Boolean, isTiming: Boolean) {
            switchVideoControlFragment(isShow, isTiming)
        }

        fun onVideoStart() {
            mediaPlayer.start()
        }

        fun onVideoPause() {
            mediaPlayer.pause()
        }
    }

    class VideoHandler(looper: Looper, owner: VideoActivity): WeakHandler<VideoActivity>(looper, owner) {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what) {
                MSG_HIDE_VCF -> {
                    getOwner()?.switchFragment(null)
                }
                MSG_HIDE_BSF -> {
                    getOwner()?.switchFragment(null)
                }
            }
        }
    }

    fun gestureUpdateSeek(distanceX: Float) {
        videoControlFragment.gestureUpdateSeek(-distanceX, displayHeight)
    }

    fun initBSFragment(contentType: BrightVolumeFragment.BsContentType) {
        if (brightVolumeFragment.isVisible && brightVolumeFragment.contentType != contentType) {
            switchBSFragment(isShow = false, isTiming = false)
        }
        brightVolumeFragment.contentType = contentType
    }

    fun gestureUpdateBright(distanceY: Float) {
        switchBSFragment(isShow = true, isTiming = true)
        brightVolumeFragment.updateBrightProgress(distanceY, displayWidth)
    }

    fun gestureUpdateVolume(distanceY: Float) {
        switchBSFragment(isShow = true, isTiming = true)
        brightVolumeFragment.updateVolumeProgress(distanceY, displayWidth)
    }

    /**
     * 手势监听
     */
    inner class VideoGestureListener: GestureDetector.SimpleOnGestureListener() {
        private var isFirstDown = false
        private var gestureType = GESTURE_TYPE_NORMAL

        /**
         * down事件，只有该事件返回true时，其他事件才能被监听
         */
        override fun onDown(e: MotionEvent?): Boolean {
            LogUtil.d(TAG, "onDown")
            isFirstDown = true
            gestureType = GESTURE_TYPE_NORMAL
            return true
        }

        /**
         * 单击事件（对双击事件做了兼容）
         */
        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            if (videoControlFragment.isVisible) {
                switchVideoControlFragment(isShow = false, isTiming = false)
            } else {
                switchVideoControlFragment(isShow = true, isTiming = true)
            }
            isFirstDown = false
            return true
        }

        /**
         * 双击事件
         */
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            switchVideoControlFragment(isShow = true, isTiming = true)
            setPlayOrPause()
            isFirstDown = false
            return true
        }

        /**
         * 滑动事件
         */
        override fun onScroll(
            e1: MotionEvent, e2: MotionEvent,
            distanceX: Float, distanceY: Float
        ): Boolean {
            if (isFirstDown) {
                gestureType = if (abs(distanceX) >= abs(distanceY)) {
                    GESTURE_TYPE_SEEK
                } else {
                    if (e1.x <= (displayHeight / 2)) {
                        initBSFragment(BrightVolumeFragment.BsContentType.TYPE_BRIGHT)
                        GESTURE_TYPE_BRIGHT
                    } else {
                        initBSFragment(BrightVolumeFragment.BsContentType.TYPE_VOLUME)
                        GESTURE_TYPE_VOLUME
                    }
                }
                isFirstDown = false
            }
            when (gestureType) {
                GESTURE_TYPE_SEEK -> {
                    gestureUpdateSeek(distanceX)
                }
                GESTURE_TYPE_BRIGHT -> {
                    gestureUpdateBright(distanceY)
                }
                GESTURE_TYPE_VOLUME -> {
                    gestureUpdateVolume(distanceY)
                }
            }
            return false
        }
    }

    companion object {
        val TAG = VideoActivity::class.java.simpleName
//        const val videoUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
        const val videoUrl = "https://media.w3.org/2010/05/sintel/trailer.mp4"

        const val MSG_HIDE_VCF = 1201
        const val MSG_HIDE_BSF = 1202
        const val HIDE_VCF_INTERVAL = 5 // 单位秒
        const val HIDE_BSF_INTERVAL = 1 // 单位秒

        const val GESTURE_TYPE_NORMAL = 0
        const val GESTURE_TYPE_SEEK = 1
        const val GESTURE_TYPE_BRIGHT = 2
        const val GESTURE_TYPE_VOLUME = 3
    }
}
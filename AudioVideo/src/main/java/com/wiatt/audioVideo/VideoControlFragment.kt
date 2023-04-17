package com.wiatt.audioVideo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.wiatt.audioVideo.customView.MySeekBar
import com.wiatt.common.LogUtil
import com.wiatt.common.TimeUtil
import kotlin.math.abs
import kotlin.math.roundToInt

class VideoControlFragment : Fragment() {

    private var videoName: String = " "
//    private var usedTime: Int = 0
//    private var surplusTime: Int = 0
//    private var totalTime: Int = 0

    private lateinit var ivBack: ImageView
    private lateinit var tvName: TextView
    private lateinit var ivScreenLock: ImageView
    private lateinit var tvTimeUsed: TextView
    private lateinit var sb: MySeekBar
    private lateinit var tvTimeSurplus: TextView
    private lateinit var ivSwitch: ImageView
    private lateinit var ivNext: ImageView

    private lateinit var callback: VideoActivity.VideoControlCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_video_control, container, false)
        ivBack = rootView.findViewById(R.id.ivBack)
        tvName = rootView.findViewById(R.id.tvName)
        ivScreenLock = rootView.findViewById(R.id.ivScreenLock)
        tvTimeUsed = rootView.findViewById(R.id.tvTimeUsed)
        sb = rootView.findViewById(R.id.sb)
        tvTimeSurplus = rootView.findViewById(R.id.tvTimeSurplus)
        ivSwitch = rootView.findViewById(R.id.ivSwitch)
        ivNext = rootView.findViewById(R.id.ivNext)

        ivSwitch.setImageResource(R.mipmap.switch_stop)
        tvName.text = videoName
        tvTimeUsed.text = "--:--"
        tvTimeSurplus.text = "--:--"
        sb.max = 100
        sb.progress = 0

        initListener()

        return rootView
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            updateView()
        } else {
            hideView()
        }
    }

    override fun onStop() {
        super.onStop()
        hideView()
    }

    private fun initListener() {
        ivBack.setOnClickListener {
            callback.onClickBack()
        }

        ivSwitch.setOnClickListener {
            callback.onSwitchMySelf(isShow = true, isTiming = true)
            callback.onClickSwitch()
        }

        sb.setOnMySeekBarChangeListener(object : MySeekBar.OnMySeekBarChangeListener {
            override fun onGetCurTime(): Int {
                return callback.onGetVideoCurTime()
            }

            override fun onProgressChanged(seekBar: MySeekBar, fromUser: Boolean, curTime: Int, totalTime: Int) {
                LogUtil.i(TAG, "fromUser = $fromUser, curTime = $curTime, totalTime = $totalTime")
                var surplusTime = totalTime - curTime
                tvTimeUsed.text = TimeUtil.stringForTime(curTime)
                tvTimeSurplus.text = TimeUtil.stringForTime(surplusTime)
            }

            override fun onStartTrackingTouch(seekBar: MySeekBar, curTime: Int, totalTime: Int) {
                callback.onVideoPause()
                callback.onSwitchMySelf(isShow = true, isTiming = false)
            }

            override fun onStopTrackingTouch(seekBar: MySeekBar, curTime: Int, totalTime: Int) {
                callback.onVideoStart()
                callback.onSwitchMySelf(isShow = true, isTiming = true)
                callback.onUpdateVideoProgress(curTime)
            }

        })
    }

    /**
     * 显示fragment后需要做的工作
     */
    private fun updateView() {
        setVideoTime(callback.onGetVideoTotalTime(), callback.onGetVideoCurTime())
    }

    /**
     * 隐藏fragment后需要做的工作
     */
    private fun hideView() {
        sb.hideSeekBar()
    }

    /**
     * 设置视频名称
     */
    fun setVideoName(videoName: String) {
        this.videoName = videoName
        tvName.text = this.videoName
    }

    /**
     * 设置视频总时长
     * @param totalDuration: 视频总时长
     */
    fun setVideoTime(totalDuration: Int, usedDuration: Int) {
        var surplusTime = totalDuration - usedDuration
        tvTimeUsed.text = TimeUtil.stringForTime(usedDuration)
        tvTimeSurplus.text = TimeUtil.stringForTime(surplusTime)
        sb.totalTime = totalDuration
        sb.curTime = usedDuration
        sb.progress = ((usedDuration.toFloat() / totalDuration.toFloat()) * 100).toInt()
        sb.showSeekBar(totalDuration)
    }

    /**
     * 更新 ivSwitch 控件
     *
     */
    fun updateIvSwitch(isPlaying: Boolean) {
        if (isPlaying) {
            ivSwitch.setImageResource(R.mipmap.switch_stop)
        } else {
            ivSwitch.setImageResource(R.mipmap.switch_start)
        }
    }

    /**
     * 根据滑动距离和屏幕长度计算出seekBar应该移动的progress，并更新seekbar
     */
    private var minDistance: Float = 0f
    private var curDistance: Float = 0f
    fun gestureUpdateSeek(distance: Float, screenLength: Int) {
        if (minDistance == 0f) {
            minDistance = screenLength.toFloat() / 50
            curDistance = 0f
        }
        curDistance += distance
        if (abs(curDistance)  >= minDistance) {
            var changeProgress: Int = ((curDistance / screenLength.toFloat()) * 50).roundToInt()
            sb.gestureMoveSeekBar(changeProgress)
            curDistance = 0f
        }
    }

    companion object {

        val TAG = VideoControlFragment::class.java.simpleName
        @JvmStatic
        fun newInstance(callback : VideoActivity.VideoControlCallback) =
            VideoControlFragment().apply {
                this.callback = callback
            }
    }
}
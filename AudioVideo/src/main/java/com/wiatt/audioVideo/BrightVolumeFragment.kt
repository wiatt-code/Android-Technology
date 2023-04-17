package com.wiatt.audioVideo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.wiatt.audioVideo.customView.ProgressSeekBar
import com.wiatt.common.LogUtil
import com.wiatt.common.SystemSettingUtil
import kotlin.math.abs
import kotlin.math.roundToInt

class BrightVolumeFragment : Fragment() {

    private lateinit var ivBS: ImageView
    private var psbBS: ProgressSeekBar? = null

    var contentType = BsContentType.TYPE_BRIGHT
    private val MAX_PROGRESS_VALUE = 100
    private val MIN_PROGRESS_VALUE = 0

    private var minDistance: Float = 0f
    private var curDistance: Float = 0f

    private var maxBrightValue = 0
    private var minBrightValue = 0
    private var brightProgress = 0
    private var maxVolumeValue = 0
    private var minVolumeValue = 0
    private var volumeProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i(TAG, "onCreate")
        SystemSettingUtil.instance.initParam(context!!)
        initValue()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogUtil.i(TAG, "onCreateView")
        var rootView = inflater.inflate(R.layout.fragment_bright_volume, container, false)
        ivBS = rootView.findViewById(R.id.ivBS)
        psbBS = rootView.findViewById(R.id.psbBS)
        psbBS?.max = MAX_PROGRESS_VALUE
        initView()
        initListener()
        return rootView
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            initView()
        }
    }

    private fun initValue() {
        val curBrightValue = SystemSettingUtil.instance.getAppBrightness(context!!)
        maxBrightValue = SystemSettingUtil.instance.getAppBrightnessMax(context!!)
        minBrightValue = SystemSettingUtil.instance.getAppBrightnessMin(context!!)
        brightProgress = ((curBrightValue.toFloat() / (maxBrightValue - minBrightValue)) * MAX_PROGRESS_VALUE).toInt()
        LogUtil.i(TAG, "curBrightValue = $curBrightValue, " +
                "maxBrightValue = $maxBrightValue, " +
                "minBrightValue = $minBrightValue, " +
                "brightProgress = $brightProgress"
        )

        val curVolumeValue = SystemSettingUtil.instance.getMediaVolume()
        maxVolumeValue = SystemSettingUtil.instance.getMediaMaxVolume()
        minVolumeValue = SystemSettingUtil.instance.getMediaMinVolume()
        volumeProgress = ((curVolumeValue.toFloat() / (maxVolumeValue - minVolumeValue)) * MAX_PROGRESS_VALUE).toInt()
        LogUtil.i(TAG, "curVolumeValue = $curVolumeValue, " +
                "maxVolumeValue = $maxVolumeValue, " +
                "minVolumeValue = $minVolumeValue, " +
                "volumeProgress = $volumeProgress")
    }

    private fun initView() {
        when (contentType) {
            BsContentType.TYPE_BRIGHT -> {
                ivBS.setImageResource(R.mipmap.bright)
                psbBS!!.progress = brightProgress
            }
            BsContentType.TYPE_VOLUME -> {
                ivBS.setImageResource(R.mipmap.volume)
                psbBS!!.progress = volumeProgress
            }
        }
    }

    private fun initListener() {
        psbBS!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                LogUtil.i(TAG, "progress = $progress, fromUser = $fromUser")
                when(contentType) {
                    BsContentType.TYPE_BRIGHT -> {
                        val curBrightValue = (maxBrightValue - minBrightValue) * (progress.toFloat() / MAX_PROGRESS_VALUE)
                        activity?.let {
                            SystemSettingUtil.instance.setAppBrightness(it.window, curBrightValue)
                        }
                    }
                    BsContentType.TYPE_VOLUME -> {
                        val curVolumeValue = ((maxVolumeValue - minVolumeValue) * (progress.toFloat() / MAX_PROGRESS_VALUE)).toInt()
                        SystemSettingUtil.instance.setMediaVolume(curVolumeValue, false)
                        val logVolume = SystemSettingUtil.instance.getMediaVolume()
                        LogUtil.i(TAG, "logVolume = $logVolume")
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // 不会被调用到
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // 不会被调用到
            }

        })
    }

    /**
     * 更新当前亮度进度
     */
    fun updateBrightProgress(distance: Float, screenLength: Int) {

        if (minDistance == 0f) {
            minDistance = screenLength.toFloat() / 50
            curDistance = 0f
        }
        curDistance += distance
        if (abs(curDistance)  >= minDistance) {
            var changeProgress: Int = ((curDistance / screenLength.toFloat()) * 50).roundToInt()
            brightProgress = with(brightProgress) {
                var value = this
                value += changeProgress
                if (value <= MIN_PROGRESS_VALUE) {
                    value = MIN_PROGRESS_VALUE
                } else if (value >= MAX_PROGRESS_VALUE) {
                    value = MAX_PROGRESS_VALUE
                }
                value
            }
            LogUtil.i(TAG, "changeProgress = $changeProgress, brightProgress = $brightProgress")
            psbBS?.progress = brightProgress
            curDistance = 0f
        }
    }

    /**
     * 更新当前音量进度
     */
    fun updateVolumeProgress(distance: Float, screenLength: Int) {
        if (minDistance == 0f) {
            minDistance = screenLength.toFloat() / 50
            curDistance = 0f
        }
        curDistance += distance
        if (abs(curDistance)  >= minDistance) {
            var changeProgress: Int = ((curDistance / screenLength.toFloat()) * 50).roundToInt()
            volumeProgress = with(volumeProgress) {
                var value = this
                value += changeProgress
                if (value <= 0) {
                    value = 0
                } else if (value >= MAX_PROGRESS_VALUE) {
                    value = MAX_PROGRESS_VALUE
                }
                value
            }
            psbBS?.progress = volumeProgress
            curDistance = 0f
        }
    }

    companion object {

        val TAG = BrightVolumeFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = BrightVolumeFragment()
    }

    /**
     * 规定BrightVolumeFragment的内容类型：bright、volume
     */
    enum class BsContentType{
        TYPE_BRIGHT, TYPE_VOLUME
    }
}
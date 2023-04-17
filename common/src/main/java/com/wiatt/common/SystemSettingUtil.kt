package com.wiatt.common

import android.app.Service
import android.content.ContentResolver
import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.provider.Settings
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi

class SystemSettingUtil {


    private var mAudioManager: AudioManager? = null

    companion object {
        val instance: SystemSettingUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SystemSettingUtil()
        }
    }

    fun initParam(context: Context) {
        mAudioManager = context.getSystemService(Service.AUDIO_SERVICE) as AudioManager
    }

    // 获取最大屏幕亮度
    fun getAppBrightnessMax(context: Context): Int{
        val maximumId: Int = context.resources.getIdentifier(
            "config_screenBrightnessSettingMaximum",
            "integer",
            "android"
        )
        return context.resources.getInteger(maximumId)
    }

    // 获取最小屏幕亮度
    fun getAppBrightnessMin(context: Context): Int{
        val minimumId: Int = context.resources.getIdentifier(
            "config_screenBrightnessSettingMinimum",
            "integer",
            "android"
        )
        return context.resources.getInteger(minimumId)
    }

    /**
     * 根据亮度值修改当前window亮度
     */
    fun setAppBrightness(window: Window, brightness: Float) {
        var lp: WindowManager.LayoutParams = window.attributes
        if (brightness == -1f) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
        } else {
            lp.screenBrightness = if (brightness <=0) {
                1 / 255f
            } else {
                brightness / 255f
            }
        }
        window.attributes = lp
    }

    // 获取当前屏幕亮度
    fun getAppBrightness(context: Context): Int{
        var value = 0
        var cr: ContentResolver = context.contentResolver
        value = try {
            Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: Settings.SettingNotFoundException) {
            -1
        }
        return value
    }

    //获取最大多媒体音量
    fun getMediaMaxVolume(): Int {
        return mAudioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ?: 100
    }

    // 获取最小多媒体音量
    fun getMediaMinVolume(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mAudioManager?.getStreamMinVolume(AudioManager.STREAM_MUSIC) ?: 0
        } else {
            0
        }
    }

    //获取当前多媒体音量
    fun getMediaVolume(): Int {
        return mAudioManager?.getStreamVolume( AudioManager.STREAM_MUSIC ) ?: 0
    }

    //获取最大通话音量
    fun getCallMaxVolume(): Int {
        return mAudioManager?.getStreamMaxVolume( AudioManager.STREAM_VOICE_CALL ) ?: 100
    }

    //获取最小通话音量
    fun getCallMinVolume(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mAudioManager?.getStreamMinVolume( AudioManager.STREAM_VOICE_CALL ) ?: 0
        } else {
            0
        }
    }

    //获取当前通话音量
    fun getCallVolume(): Int {
        return mAudioManager?.getStreamVolume( AudioManager.STREAM_VOICE_CALL ) ?: 0
    }

    //获取最大系统音量
    fun getSystemMaxVolume(): Int {
        return mAudioManager?.getStreamMaxVolume(AudioManager.STREAM_SYSTEM ) ?: 100
    }

    //获取最大系统音量
    fun getSystemMinVolume(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mAudioManager?.getStreamMinVolume(AudioManager.STREAM_SYSTEM ) ?: 0
        } else {
            0
        }
    }

    //获取当前系统音量
    fun getSystemVolume(): Int {
        return mAudioManager?.getStreamVolume(AudioManager.STREAM_SYSTEM ) ?: 0
    }

    //获取最大提示音量
    fun getAlermMaxVolume(): Int {
        return mAudioManager?.getStreamMaxVolume(AudioManager.STREAM_ALARM ) ?: 100
    }

    //获取最大提示音量
    @RequiresApi(Build.VERSION_CODES.P)
    fun getAlermMinVolume(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mAudioManager?.getStreamMinVolume(AudioManager.STREAM_ALARM ) ?: 0
        } else {
            0
        }
    }

    //获取当前提示音量
    fun getAlermVolume(): Int {
        return mAudioManager?.getStreamVolume(AudioManager.STREAM_ALARM ) ?: 0
    }

    // 设置多媒体音量
    fun setMediaVolume(volume: Int, isShowUi: Boolean = true) {
        var flag = AudioManager.FLAG_PLAY_SOUND
        if (isShowUi) {
            flag = AudioManager.FLAG_PLAY_SOUND or AudioManager.FLAG_SHOW_UI
        }
        mAudioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, volume, flag)
    }

    //设置通话音量
    fun setCallVolume(volume: Int, isShowUi: Boolean = true) {
        var flag = AudioManager.FLAG_PLAY_SOUND
        if (isShowUi) {
            flag = AudioManager.FLAG_PLAY_SOUND or AudioManager.FLAG_SHOW_UI
        }
        mAudioManager?.setStreamVolume( AudioManager.STREAM_VOICE_CALL, volume, flag)
    }

    //设置提示音量
    fun setAlermVolume(volume: Int, isShowUi: Boolean = true) {
        var flag = AudioManager.FLAG_PLAY_SOUND
        if (isShowUi) {
            flag = AudioManager.FLAG_PLAY_SOUND or AudioManager.FLAG_SHOW_UI
        }
        mAudioManager?.setStreamVolume( AudioManager.STREAM_ALARM, volume, flag)
    }
}
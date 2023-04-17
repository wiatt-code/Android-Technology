package com.wiatt.audioVideo.player

import android.view.SurfaceHolder

interface Player {

    /**
     * 设置surfaceHolder
     */
    fun setDisplay(surfaceHolder: SurfaceHolder)

    /**
     * 重置播放器
     */
    fun reset()

    /**
     * 设置播放源
     */
    fun setDataSource(dataSource: String)

    /**
     * 同步准备播放
     */
    fun prepare()

    /**
     * 异步准备播放
     */
    fun prepareAsync()

    /**
     * 开始播放
     */
    fun start()

    /**
     * 判断是否处于播放中
     */
    fun isPlaying(): Boolean

    /**
     * 暂停播放
     */
    fun pause()

    /**
     * 是否处于暂停播放状态
     */
    fun isPause(): Boolean

    /**
     * 停止播放
     */
    fun stop()

    /**
     * 调整播放位置
     * @param pos：单位毫秒（ms）
     */
    fun seekTo(pos: Int)

    /**
     * 是否正在调整播放位置
     */
    fun isSeeking(): Boolean

    /**
     * 释放播放器
     */
    fun release()

    /**
     * 获取播放状态
     */
    fun getPlayState(): PlayerState

    /**
     * 获取播放速度
     */
    fun getRate(): Float

    /**
     * 设置播放速度
     */
    fun setRate(rate: Float)

    /**
     * 获取播放源的总时长
     * @return 单位毫秒（ms）
     */
    fun getDuration(): Int

    /**
     * 获取当前的播放位置
     * @return 单位毫秒（ms）
     */
    fun getCurrentPosition(): Int
}
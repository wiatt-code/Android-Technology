package com.wiatt.audioVideo.player

enum class PlayerState {
    /**
     * 空闲状态
     */
    IDLE,

    /**
     * 初始化完成状态(已设置播放源)
     */
    INITIALIZED,

    /**
     * 准备完成状态
     */
    PREPARED,

    /**
     * 正在播放状态
     */
    PLAYING,

    /**
     * 调整播放位置状态
     */
    SEEKING,

    /**
     * 播放暂停状态
     */
    PAUSED,

    /**
     * 播放停止状态
     */
    STOPPED,

    /**
     * 播放完成状态
     */
    COMPLETED,

    /**
     * 播放错误状态
     */
    ERROR
}
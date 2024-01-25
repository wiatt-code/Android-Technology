package com.wiatt.custom_view.fragment_viewpager2

/**
 * @author: wiatt
 * @description:
 */
data class ItemDataInfo(
    // 用户id
    var id: String,
    // 昵称
    var nickName: String,
    // 角色
    var role: Int,
    // 麦克风状态
    var micStatus: Boolean,
    // 摄像头状态
    var cameraStatus: Boolean,
    // 该成员是否是自己
    var isMe: Boolean = false
    ) {

    companion object {
        // 成员角色：创建者
        const val ITEM_ROLE_CREATOR = 1
        // 成员角色：主持人
        const val ITEM_ROLE_COMPERE = 2
        // 成员角色：普通成员
        const val ITEM_ROLE_NORMAL = 3
    }
}

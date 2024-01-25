package com.wiatt.custom_view.fragment_viewpager2

import androidx.lifecycle.MutableLiveData
import com.wiatt.common.mvvm.BaseViewModel

class FragmentViewpager2ViewModel():
    BaseViewModel<FragmentViewpager2Model, FragmentViewpager2Contract.IFragmentViewpager2ViewModel>() {

    // 自己的昵称 liveData
    val myNickNameLiveData: MutableLiveData<String> = MutableLiveData()
    // 自己的麦克风状态 liveData
    val myMicStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()
    // 自己的照相机状态 liveData
    val myCameraStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()
    // 展示哪一页二级 fragment liveData
    val showSecFragmentLiveData: MutableLiveData<String> = MutableLiveData()

    // 从 ViewModel 回调数据到界面
    private var viewListeners: MutableList<FragmentViewpager2Listener> = mutableListOf()

    // 自己的昵称
    var myNickName: String? = null
    // 自己的麦克风状态
    var myMicStatus: Boolean = false
    // 自己的照相机状态
    var myCameraStatus: Boolean = false

    // 成员列表
    var itemList: MutableList<ItemDataInfo> = mutableListOf()

    override fun getModel(): FragmentViewpager2Model {
        return FragmentViewpager2Model(FragmentViewpager2ViewModelImpl())
    }

    fun addListener(listener: FragmentViewpager2Listener) {
        if (!viewListeners.contains(listener)) {
            viewListeners.add(listener)
        }
    }

    fun removeListener(listener: FragmentViewpager2Listener) {
        if (viewListeners.contains(listener)) {
            viewListeners.remove(listener)
        }
    }

    /**
     * 更新初始参数
     */
    fun updateInitialValue(nickName: String) {
        myNickName = nickName
        myMicStatus = false
        myCameraStatus = false
        itemList.add(ItemDataInfo("0", nickName, ItemDataInfo.ITEM_ROLE_NORMAL, myMicStatus, myCameraStatus))
//        itemList.add(ItemDataInfo("1", "成员-1", ItemDataInfo.ITEM_ROLE_COMPERE, false, false))
//        itemList.add(ItemDataInfo("2", "成员-2", ItemDataInfo.ITEM_ROLE_NORMAL, true, false))
//        itemList.add(ItemDataInfo("3", "成员-3", ItemDataInfo.ITEM_ROLE_NORMAL, false, true))
//        itemList.add(ItemDataInfo("4", "成员-4", ItemDataInfo.ITEM_ROLE_NORMAL, false, true))
    }

    /**
     * 获取自己的昵称
     */
    fun getMyNickName() {
        // 获取自己的昵称
        myNickName?.let {
            myNickNameLiveData.value = it
        }
    }

    /**
     * 获取自己的麦克风状态
     */
    fun getMyMicStatus() {
        myMicStatusLiveData.value = myMicStatus
    }

    /**
     * 切换麦克风状态
     */
    fun switchMyMicStatus() {
        // 执行开关麦克风的操作
        myMicStatus = !myMicStatus
        myMicStatusLiveData.value = myMicStatus
    }

    /**
     * 获取自己的照相机状态
     */
    fun getMyCameraStatus() {
        myCameraStatusLiveData.value = myCameraStatus
    }

    /**
     * 切换照相机状态
     */
    fun switchMyCameraStatus() {
        // 执行开关照相机的操作
        myCameraStatus = !myCameraStatus
        myCameraStatusLiveData.value = myCameraStatus
        checkSecFragment()
    }

    /**
     * 选择二级 fragment
     */
    fun checkSecFragment() {
        if (itemList.size <= 1) {
            // 如果会议成员列表中只有自己
            if (myCameraStatus) {
                // 如果视频是开启的
                showSecFragmentLiveData.value = FragmentViewpager2Fragment.FRAGMENT_SEC_SOLO_VIDEO
            } else {
                // 如果视频是关闭的
                showSecFragmentLiveData.value = FragmentViewpager2Fragment.FRAGMENT_SEC_SOLO_AVATAR
            }
        } else {
            // 如果会议成员列表有多名成员
            showSecFragmentLiveData.value = FragmentViewpager2Fragment.FRAGMENT_SEC_MULTI_MIX
        }
    }

    var testNum: Int = 0
    fun addMoreItem() {
        testNum = itemList.size
        val list = mutableListOf<ItemDataInfo>()
        list.add(ItemDataInfo((++testNum).toString(), "成员-$testNum", ItemDataInfo.ITEM_ROLE_NORMAL, true, true))
        list.add(ItemDataInfo((++testNum).toString(), "成员-$testNum", ItemDataInfo.ITEM_ROLE_NORMAL, false, false))
        list.add(ItemDataInfo((++testNum).toString(), "成员-$testNum", ItemDataInfo.ITEM_ROLE_NORMAL, false, false))
        list.add(ItemDataInfo((++testNum).toString(), "成员-$testNum", ItemDataInfo.ITEM_ROLE_NORMAL, false, true))
        list.add(ItemDataInfo((++testNum).toString(), "成员-$testNum", ItemDataInfo.ITEM_ROLE_NORMAL, false, false))
        list.add(ItemDataInfo((++testNum).toString(), "成员-$testNum", ItemDataInfo.ITEM_ROLE_NORMAL, false, true))
        list.add(ItemDataInfo((++testNum).toString(), "成员-$testNum", ItemDataInfo.ITEM_ROLE_NORMAL, false, false))
        list.add(ItemDataInfo((++testNum).toString(), "成员-$testNum", ItemDataInfo.ITEM_ROLE_NORMAL, false, true))
        list.add(ItemDataInfo((++testNum).toString(), "成员-$testNum", ItemDataInfo.ITEM_ROLE_NORMAL, false, false))

        list.forEach { memberInfo ->
            itemList.add(memberInfo)
            viewListeners.forEach {
                it.onItemListAddOne(itemList.size - 1)
            }
        }
        if (itemList.size > 1) {
            checkSecFragment()
        }
    }

    fun addOneItem() {
        testNum = itemList.size
        val memberInfo = ItemDataInfo(testNum.toString(), "成员-$testNum" , ItemDataInfo.ITEM_ROLE_NORMAL, false, false)
        itemList.add(memberInfo)
        viewListeners.forEach {
            it.onItemListAddOne(itemList.size - 1)
        }
        if (itemList.size > 1) {
            checkSecFragment()
        }
    }

    fun removeOneItem(position: Int) {
        if (position < 0 || itemList.size <= position) {
            return
        }
        itemList.removeAt(position)
        viewListeners.forEach {
            it.onItemListRemoveOne(position)
        }
        if (itemList.size <= 1) {
            checkSecFragment()
        }
    }

    fun updateItem(position: Int) {
        if (position < 0 || itemList.size <= position) {
            return
        }

        val itemDataInfo = itemList[position]
        itemDataInfo.micStatus = !itemDataInfo.micStatus
        itemDataInfo.cameraStatus = !itemDataInfo.cameraStatus
        itemDataInfo.nickName = itemDataInfo.nickName + "改"
        itemList[position] = itemDataInfo
        viewListeners.forEach {
            it.onUpdateItem(position, itemDataInfo)
        }
    }

    inner class FragmentViewpager2ViewModelImpl: FragmentViewpager2Contract.IFragmentViewpager2ViewModel {

    }

    interface FragmentViewpager2Listener {
        // 添加一个参会成员
        fun onItemListAddOne(position: Int)
        //移除一个参会成员
        fun onItemListRemoveOne(position: Int)
        // 更新一个参会成员
        fun onUpdateItem(position: Int, itemDataInfo: ItemDataInfo)
    }
}
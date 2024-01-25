package com.wiatt.custom_view.fragment_viewpager2

import android.os.Looper
import android.os.Message
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.wiatt.common.WeakHandler

/**
 * @author: wiatt
 * @description: viewpager2 的适配器
 */
class ItemMultiStatePageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle, var fragments: MutableList<MultiListFragment>, var viewModel: FragmentViewpager2ViewModel):
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val mFragmentHashCodes: MutableList<Long> = mutableListOf()
    private val handler: ItemMultiAdapterHandler = ItemMultiAdapterHandler(Looper.getMainLooper(), this)

    init {
        fragments.forEach {
            mFragmentHashCodes.add(it.hashCode().toLong())
        }
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder, position: Int, payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    // 按照此方法重写 getItemId、containsItem 函数，数据源中fragment的hashcode 一对一 增删时注意保持一致
    // 可以解决删除 fragment 时，删除内容不一致的问题，以及其附带的其他崩溃问题
    override fun getItemId(position: Int): Long {
        return fragments[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return mFragmentHashCodes.contains(itemId)
    }

    fun insertFragment(fragmentItem: MultiListFragment) {
        fragments.add(fragmentItem)
        mFragmentHashCodes.add(fragmentItem.hashCode().toLong())
        notifyItemInserted(fragments.size - 1)
    }

    fun removeFragment(position: Int) {
        if (position >= itemCount) {
            return
        }
        fragments.removeAt(position)
        mFragmentHashCodes.removeAt(position)
        notifyItemRemoved(position)

        handler.removeMessages(HANDLER_MSG_UPDATE_FRAGMENT)
        handler.sendHandlerMessage(HANDLER_MSG_UPDATE_FRAGMENT,
            null, -1, -1, 500
        )
    }

    fun updateMultiListPage(targetPageIndex: Int, targetItemIndex: Int) {
        for (index in targetPageIndex until fragments.size) {
            if (index == targetPageIndex) {
                fragments[index].updatePageItems(targetItemIndex)
            } else {
                fragments[index].updatePageItems(0)
            }
        }
    }

    private val HANDLER_MSG_UPDATE_FRAGMENT = 1001

    class ItemMultiAdapterHandler(looper: Looper, owner: ItemMultiStatePageAdapter):
        WeakHandler<ItemMultiStatePageAdapter>(looper, owner) {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            getOwner()?.let { owner ->
                when(msg.what) {
                    owner.HANDLER_MSG_UPDATE_FRAGMENT -> {
                        owner.fragments.forEachIndexed { index, multiListFragment ->
                            multiListFragment.updatePageIndex(index)
                        }
                    }
                }
            }
        }
    }
}
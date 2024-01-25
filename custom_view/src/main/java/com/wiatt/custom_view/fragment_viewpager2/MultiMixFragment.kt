package com.wiatt.custom_view.fragment_viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.wiatt.common.base.BaseFragment
import com.wiatt.custom_view.databinding.FragmentMultiMixBinding

/**
 * @author: wiatt
 * @description: 多人混合页面，viewpager2 页面
 */
class MultiMixFragment : BaseFragment() {

    private lateinit var viewModel: FragmentViewpager2ViewModel

    private var _binding: FragmentMultiMixBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemMultiAdapter: ItemMultiStatePageAdapter
    private var viewListenerImpl: FragmentViewpager2ListenerImpl = FragmentViewpager2ListenerImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[FragmentViewpager2ViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMultiMixBinding.inflate(inflater, container, false)
        val rootView = binding.root

        initLiveData()
        viewModel.addListener(viewListenerImpl)
        initListener()
        initPage()

        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.removeListener(viewListenerImpl)
    }

    private fun initLiveData() {

    }

    private fun initListener() {

    }

    private fun initPage() {
        val fragments = mutableListOf<MultiListFragment>()
        val totalPage = calculatePageNum()
        for (index in 0 until totalPage) {
            fragments.add(MultiListFragment.newInstance(index))
        }

        binding.memberMultiVp.offscreenPageLimit = 1
        binding.memberMultiVp.isSaveEnabled = false
        itemMultiAdapter = ItemMultiStatePageAdapter(childFragmentManager, lifecycle, fragments, viewModel)
        binding.memberMultiVp.adapter = itemMultiAdapter
    }

    /**
     * 计算当前总页数
     */
    private fun calculatePageNum(): Int {
        val addOne = if (viewModel.itemList.size % 4 == 0) {
            0
        } else {
            1
        }
        return viewModel.itemList.size / 4 + addOne
    }

    companion object {

        @JvmStatic
        fun newInstance() = MultiMixFragment()
    }

    inner class FragmentViewpager2ListenerImpl: FragmentViewpager2ViewModel.FragmentViewpager2Listener {
        override fun onItemListAddOne(position: Int) {
            val totalPage = calculatePageNum()
            if (totalPage > itemMultiAdapter.itemCount) {
                for (index in itemMultiAdapter.itemCount until totalPage) {
                    itemMultiAdapter.insertFragment(MultiListFragment.newInstance(index))
                }
            }
        }

        override fun onItemListRemoveOne(position: Int) {
            if (viewModel.itemList.size == 0) {
                return
            }

            val totalPage = calculatePageNum()
            val targetPageIndex = position / 4
            val targetItemIndex = position % 4
            itemMultiAdapter.updateMultiListPage(targetPageIndex, targetItemIndex)
            if (totalPage < itemMultiAdapter.itemCount) {
                itemMultiAdapter.removeFragment(itemMultiAdapter.itemCount - 1)
            }
        }

        override fun onUpdateItem(position: Int, itemDataInfo: ItemDataInfo) {

        }
    }
}
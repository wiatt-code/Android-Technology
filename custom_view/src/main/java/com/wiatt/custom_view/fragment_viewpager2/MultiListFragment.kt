package com.wiatt.custom_view.fragment_viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.wiatt.common.base.BaseFragment
import com.wiatt.custom_view.R
import com.wiatt.custom_view.databinding.FragmentMultiListBinding

/**
 * @author: wiatt
 * @description: 多人混合页面，viewpager2 中的一个 page，每个pager包含多个item
 */
class MultiListFragment : BaseFragment() {
    private val ARG_INDEX = "index"

    private lateinit var viewModel: FragmentViewpager2ViewModel
    private var viewListenerImpl: FragmentViewpager2ListenerImpl = FragmentViewpager2ListenerImpl()

    private var _binding: FragmentMultiListBinding? = null
    private val binding get() = _binding!!

    // fragment 数组，目前有四个，在 switchFragment 函数中创建和更新
    private val fragmentArray: Array<MultiItemFragment?> = Array(totalItemCount) {
        null
    }
    // fragment 容器数组，应与 fragmentArray 数组长度匹配
    private lateinit var containerArray: Array<Int>
    // 当前 fragment 在 viewPager2 中的索引，主要用于计算需要显示的成员的索引
    private var mIndex: Int = 0

    var isCreate: Boolean = false
        private set
    var isShow: Boolean = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mIndex = it.getInt(ARG_INDEX, 0)
        }
        viewModel = ViewModelProvider(requireActivity())[FragmentViewpager2ViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMultiListBinding.inflate(inflater, container, false)
        val rootView = binding.root
        isCreate = true

        containerArray = Array(totalItemCount) {
            when(it) {
                0 -> R.id.containerZeroFl
                1 -> R.id.containerOneFl
                2 -> R.id.containerTwoFl
                3 -> R.id.containerThreeFl
                else -> R.id.containerZeroFl
            }
        }
        binding.flagTv.text = "测试Flag，当前页：$mIndex"
        initLiveData()
        viewModel.addListener(viewListenerImpl)
        initView()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        isShow = true
    }

    override fun onPause() {
        super.onPause()
        isShow = false
    }

    override fun onDestroy() {
        super.onDestroy()
        isCreate = false
        viewModel.removeListener(viewListenerImpl)
    }

    private fun initLiveData() {
    }

    private fun initView() {
        childFragmentManager.fragments.forEach {
            removeFragment(it)
        }
        val startItemIndex = mIndex * totalItemCount
        for (i in 0 until totalItemCount) {
            val itemIndex = startItemIndex + i
            if (itemIndex < viewModel.itemList.size) {
                val memberInfo = viewModel.itemList[itemIndex]
                switchFragment(memberInfo, i)
            }
        }
    }

    /**
     * 更新当前页所处的索引
     */
    fun updatePageIndex(index: Int) {
        mIndex = index
        arguments?.putInt(ARG_INDEX, index)
        if (isCreate) {
            binding.flagTv.text = "测试Flag，当前页：$mIndex"
        }
    }

    /**
     * 更新页面项
     */
    fun updatePageItems(startItemIndex: Int) {
        if (!isCreate) {
            return
        }
        val startMemberIndex = mIndex * totalItemCount
        for (index in startItemIndex until totalItemCount) {
            val memberIndex = startMemberIndex + index
            switchFragment(null, index)
            if (memberIndex < viewModel.itemList.size) {
                val memberInfo = viewModel.itemList[memberIndex]
                switchFragment(memberInfo, index)
            }
        }
    }

    /**
     * 新增一个成员
     */
    private fun addOneItem(position: Int) {
        if (!isCreate) {
            return
        }
        val startMemberIndex = mIndex * totalItemCount
        if (position >= startMemberIndex && position < startMemberIndex + totalItemCount) {
            if (position < viewModel.itemList.size) {
                val memberInfo = viewModel.itemList[position]
                switchFragment(memberInfo, position % totalItemCount)
            }
        }
    }

    /**
     * 用于切换Fragment
     * 当不显示fragment时，nextFragment为空
     * 该函数必须根据 totalItemCount 的值而改变
     */
    private fun switchFragment(itemDataInfo: ItemDataInfo?, itemIndex: Int): Boolean{

        if (itemIndex >= fragmentArray.size || itemIndex >= containerArray.size) return false
        var fragment = fragmentArray[itemIndex]
        if (itemDataInfo == null) {
            if (fragment?.isAdded == true) {
                hideFragment(fragment)
            }
        } else {
            if (fragment == null) {
                fragment = MultiItemFragment.newInstance(itemDataInfo.nickName, itemDataInfo.micStatus, itemDataInfo.cameraStatus)
            } else {
                fragment.updateMember(itemDataInfo.nickName, itemDataInfo.micStatus, itemDataInfo.cameraStatus)
            }
            if (fragment.isAdded) {
                showFragment(fragment)
            } else {
                addFragment(fragment, containerArray[itemIndex])
            }
            fragmentArray[itemIndex] = fragment
        }
        return true
    }

    companion object {

        const val totalItemCount = 4

        @JvmStatic
        fun newInstance(indexParam: Int) =
            MultiListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_INDEX, indexParam)
                }
            }
    }

    inner class FragmentViewpager2ListenerImpl: FragmentViewpager2ViewModel.FragmentViewpager2Listener {
        override fun onItemListAddOne(position: Int) {
            addOneItem(position)
        }

        override fun onItemListRemoveOne(position: Int) {

        }

        override fun onUpdateItem(position: Int, itemDataInfo: ItemDataInfo) {
            if (!isCreate) {
                return
            }
            val startMemberIndex = mIndex * totalItemCount
            if (position >= startMemberIndex && position < startMemberIndex + totalItemCount) {
                val itemIndex = position % totalItemCount
                if (itemIndex < fragmentArray.size) {
                    fragmentArray[itemIndex]?.updateMember(itemDataInfo.nickName, itemDataInfo.micStatus, itemDataInfo.cameraStatus)
                }
            }
        }

    }
}
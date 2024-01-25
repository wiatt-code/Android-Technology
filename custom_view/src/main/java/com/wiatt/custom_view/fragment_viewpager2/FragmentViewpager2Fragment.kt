package com.wiatt.custom_view.fragment_viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wiatt.common.base.BaseFragment
import com.wiatt.custom_view.R
import com.wiatt.custom_view.databinding.FragmentFragmentViewpager2Binding

/**
 * @author: wiatt
 * @description: 和 activity 对接的最外侧 fragment，用于判断该显示哪一个内层fragment
 */
class FragmentViewpager2Fragment : BaseFragment() {

    private var _binding: FragmentFragmentViewpager2Binding? = null
    private val binding get() = _binding!!
    private var currentFragment: Fragment? = null

    private lateinit var viewModel: FragmentViewpager2ViewModel

    private lateinit var nickName: String

    private var soloVideoFragment: SoloVideoFragment? = null
    private var soloAvatarFragment: SoloAvatarFragment? = null
    private var multiMixFragment: MultiMixFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[FragmentViewpager2ViewModel::class.java]
        nickName = "该叫什么呢"
        viewModel.updateInitialValue(nickName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFragmentViewpager2Binding.inflate(inflater, container, false)
        val rootView = binding.root

        initLiveData()
        initView()
        initListener()

        return rootView
    }

    private fun initLiveData() {
        viewModel.showSecFragmentLiveData.observe(viewLifecycleOwner) {
            when(it) {
                FRAGMENT_SEC_SOLO_VIDEO -> showSoloVideoPage()
                FRAGMENT_SEC_SOLO_AVATAR -> showSoloAvatarPage()
                FRAGMENT_SEC_MULTI_MIX -> showMultiMixPage()
            }
        }
    }

    private fun initView() {
        viewModel.checkSecFragment()
    }

    private fun initListener() {
        binding.addOneItemTv.setOnClickListener {
            viewModel.addOneItem()
        }
        binding.addMoreItemTv.setOnClickListener {
            viewModel.addMoreItem()
        }
        binding.removeOneItemTv.setOnClickListener {
            val removeNum = binding.removeOneItemEt.text.toString().toInt()
            viewModel.removeOneItem(removeNum)
        }
        binding.updateOneItemTv.setOnClickListener {
            val updateMemberNum = binding.updateOneItemEt.text.toString().toInt()
            viewModel.updateItem(updateMemberNum)
        }
    }

    /**
     * 展示单人视频页
     */
    @Synchronized
    private fun showSoloVideoPage() {
        if (soloVideoFragment == null) {
            // 获取准确的用户昵称和当前麦克风是否开启
            soloVideoFragment = SoloVideoFragment.newInstance()
        }
        switchFragment(soloVideoFragment)
    }

    /**
     * 展示单人头像页
     */
    @Synchronized
    private fun showSoloAvatarPage() {
        if (soloAvatarFragment == null) {
            // 获取准确的用户昵称和当前麦克风是否开启
            soloAvatarFragment = SoloAvatarFragment.newInstance()
        }
        switchFragment(soloAvatarFragment)
    }

    /**
     * 展示多人混合页
     */
    @Synchronized
    private fun showMultiMixPage() {
        if (multiMixFragment == null) {
//            multiMixFragment = MultiMixOFragment.newInstance()
            multiMixFragment = MultiMixFragment.newInstance()
        }
        switchFragment(multiMixFragment)
    }

    /**
     * 用于切换Fragment
     * 当不显示fragment时，nextFragment为空
     */
    private fun switchFragment(nextFragment: Fragment?): Boolean{
        if(nextFragment == currentFragment) {
            return false
        }

        if (currentFragment != null && currentFragment!!.isAdded) {
            hideFragment(currentFragment!!)
        }

        if (nextFragment != null) {
            if (nextFragment.isAdded) {
                showFragment(nextFragment)
            } else {
                addFragment(nextFragment, R.id.contentSecFl)
            }
        }
        currentFragment = nextFragment
        return true
    }

    companion object {
        // 标识：单人视频 fragment
        val FRAGMENT_SEC_SOLO_VIDEO = "solo_video_fragment"
        // 标识：单人头像 fragment
        val FRAGMENT_SEC_SOLO_AVATAR = "solo_avatar_fragment"
        // 标识：多人混合 fragment
        val FRAGMENT_SEC_MULTI_MIX = "multi_mix_fragment"

        @JvmStatic
        fun newInstance() = FragmentViewpager2Fragment()
    }
}
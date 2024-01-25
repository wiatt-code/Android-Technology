package com.wiatt.custom_view.fragment_viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.wiatt.common.base.BaseFragment
import com.wiatt.custom_view.databinding.FragmentSoloAvatarBinding

/**
 * @author: wiatt
 * @description: 单人头像页面
 */
class SoloAvatarFragment : BaseFragment() {

    private lateinit var viewModel: FragmentViewpager2ViewModel

    private var _binding: FragmentSoloAvatarBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[FragmentViewpager2ViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSoloAvatarBinding.inflate(inflater, container, false)
        val rootView = binding.root

        initLiveData()
        initView()

        return rootView
    }

    private fun initLiveData() {
        viewModel.myNickNameLiveData.observe(viewLifecycleOwner) {
            binding.nickNameTv.text = it
        }
        viewModel.myMicStatusLiveData.observe(viewLifecycleOwner) {
            binding.micSmallIv.isSelected = it
        }
    }

    private fun initView() {
        viewModel.getMyNickName()
        viewModel.getMyMicStatus()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SoloAvatarFragment()
    }
}
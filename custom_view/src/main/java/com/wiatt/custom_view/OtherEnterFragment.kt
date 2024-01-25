package com.wiatt.custom_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wiatt.common.base.BaseFragment
import com.wiatt.custom_view.databinding.FragmentOtherEnterBinding
import com.wiatt.custom_view.fragment_viewpager2.FragmentViewpager2Activity

class OtherEnterFragment : BaseFragment() {

    private var _binding: FragmentOtherEnterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtherEnterBinding.inflate(inflater, container, false)
        val rootView = binding.root

        initListener()

        return rootView
    }

    private fun initListener() {
        binding.fvBtn.setOnClickListener {
            FragmentViewpager2Activity.startFragmentViewPager2Page(this@OtherEnterFragment.requireContext())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = OtherEnterFragment()
    }
}
package com.wiatt.custom_view.fragment_viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wiatt.common.base.BaseFragment
import com.wiatt.custom_view.databinding.FragmentMultiItemBinding

/**
 * @author: wiatt
 * @description: 多人混合页面，一个 item 项
 */
class MultiItemFragment : BaseFragment() {

    private var ARG_NICKNAME = "nickName"
    private var ARG_AUDIO_STATUS = "audioStatus"
    private var ARG_VIDEO_STATUS = "videoStatus"

    private var _binding: FragmentMultiItemBinding? = null
    private val binding get() = _binding!!

    // 昵称
    private var mNickName: String? = null
    // 音频状态，可能受各种因素共同作用，也可能不为 boolean 类型
    private var mAudioStatus: Boolean = false
    // 视频状态，可能受各种因素共同作用，也可能不为 boolean 类型
    private var mVideoStatus: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mNickName = it.getString(ARG_NICKNAME, "")
            mAudioStatus = it.getBoolean(ARG_AUDIO_STATUS, false)
            mVideoStatus = it.getBoolean(ARG_VIDEO_STATUS, false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMultiItemBinding.inflate(inflater, container, false)
        val rootView = binding.root

        initView()

        return rootView
    }

    private fun initView() {
        if (mVideoStatus) {
            binding.placeholderIv.visibility = View.VISIBLE
            binding.avatarIv.visibility = View.GONE
        } else {
            binding.placeholderIv.visibility = View.GONE
            binding.avatarIv.visibility = View.VISIBLE
        }
        binding.videoSmallIv.isSelected = mVideoStatus
        binding.micSmallIv.isSelected = mAudioStatus
        binding.nickNameTv.text = mNickName
    }

    fun updateMember(nickname: String, audioStatus: Boolean, videoStatus: Boolean) {
        updateNickName(nickname)
        updateAudioStatus(audioStatus)
        updateVideoStatus(videoStatus)
    }

    fun updateNickName(nickname: String) {
        mNickName = nickname
        arguments?.putString(ARG_NICKNAME, mNickName)
        binding.nickNameTv.text = mNickName
    }

    fun updateAudioStatus(audioStatus: Boolean) {
        mAudioStatus = audioStatus
        arguments?.putBoolean(ARG_AUDIO_STATUS, audioStatus)
        binding.micSmallIv.isSelected = mAudioStatus
    }

    fun updateVideoStatus(videoStatus: Boolean) {
        mVideoStatus = videoStatus
        arguments?.putBoolean(ARG_VIDEO_STATUS, videoStatus)
        if (mVideoStatus) {
            binding.placeholderIv.visibility = View.VISIBLE
            binding.avatarIv.visibility = View.GONE
        } else {
            binding.placeholderIv.visibility = View.GONE
            binding.avatarIv.visibility = View.VISIBLE
        }
        binding.videoSmallIv.isSelected = mVideoStatus
    }

    companion object {

        @JvmStatic
        fun newInstance(nickname: String, audioStatus: Boolean, videoStatus: Boolean) =
            MultiItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NICKNAME, nickname)
                    putBoolean(ARG_AUDIO_STATUS, audioStatus)
                    putBoolean(ARG_VIDEO_STATUS, videoStatus)
                }
            }
    }
}
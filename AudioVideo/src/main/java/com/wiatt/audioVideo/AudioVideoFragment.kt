package com.wiatt.audioVideo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.wiatt.audioVideo.eventBusBean.EventPlayerError
import com.wiatt.audioVideo.eventBusBean.EventPlayerState
import com.wiatt.audioVideo.eventBusBean.EventPlayerVideoSize
import com.wiatt.audioVideo.player.PlayerState
import com.wiatt.common.base.BaseFragment
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@Route(path = "/audioVideo/AudioVideoFragment")
class AudioVideoFragment : BaseFragment() {

    lateinit var btnGoVideo: Button
    lateinit var btnGoAudio: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_audio_video, container, false)
        btnGoVideo = rootView.findViewById(R.id.btnGoVideo)
        btnGoAudio = rootView.findViewById(R.id.btnGoAudio)
        btnGoVideo.setOnClickListener {
            startActivity(Intent(context,VideoActivity::class.java))
        }
        btnGoAudio.setOnClickListener {
            Toast.makeText(context, "功能建设中", Toast.LENGTH_LONG).show()
        }
        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() = AudioVideoFragment()
    }
}
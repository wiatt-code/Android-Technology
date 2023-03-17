package com.wiatt.simpledemo.myFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.wiatt.simpledemo.R

class FragmentTest1: Fragment() {

    private lateinit var btnF2: Button
    private lateinit var btnF3: Button
    private lateinit var btnF4: Button
    private var onMsgSendListener: OnMsgSendListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView = inflater.inflate(R.layout.fragment_test_1, container, false)
        btnF2 = rootView.findViewById(R.id.btnF2)
        btnF3 = rootView.findViewById(R.id.btnF3)
        btnF4 = rootView.findViewById(R.id.btnF4)
        btnF2.text = "fragment_2"
        btnF3.text = "fragment_3"
        btnF4.text = "fragment_4"
        btnF2.setOnClickListener(MyOnClinckListener())
        btnF3.setOnClickListener(MyOnClinckListener())
        btnF4.setOnClickListener(MyOnClinckListener())
        return rootView
    }

    companion object {
        private const val ARG_TEXT = "param"
        fun newInstance(param: String): FragmentTest1{
            val fragment = FragmentTest1()
            val args = Bundle()
            args.putString(ARG_TEXT, param)
            fragment.arguments = args
            return fragment
        }
    }

    inner class MyOnClinckListener: View.OnClickListener {
        override fun onClick(v: View) {
            when (v.id) {
                R.id.btnF2 -> {
                    onMsgSendListener?.fragmentChange(FragmentTest2::class.java.name)
                }
                R.id.btnF3 -> {
                    onMsgSendListener?.fragmentChange(FragmentTest3::class.java.name)
                }
                R.id.btnF4 -> {
                    onMsgSendListener?.fragmentChange(FragmentTest4::class.java.name)
                }
            }
        }
    }

    fun setMsgSendListener(onMsgSendListener: OnMsgSendListener) {
        this.onMsgSendListener = onMsgSendListener
    }

    /**
     * fragment与activity通信
     * 1、通过构造器
     * 2、通过广播
     * 3、通过EventBus
     * 4、通过接口回调
     * 5、通过ViewModel
     * 6、通过Handler
     */
    interface OnMsgSendListener {
        fun fragmentChange(nextFragmentName: String)
    }
}
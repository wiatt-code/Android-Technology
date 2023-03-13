package com.wiatt.plugin.myViewPager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.wiatt.plugin.R


class BlankFragment: Fragment() {


    private var mTextStr: String? = null
    private var rootView: View? = null


    companion object {
        private const val ARG_TEXT = "param"
        fun newInstance(param: String): BlankFragment {
            val fragment = BlankFragment()
            var args = Bundle()
            args.putString(ARG_TEXT, param)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTextStr = arguments?.getString(ARG_TEXT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_blank, container, false)
        }
        initView()
        return rootView
    }

    private fun initView() {
        val textView = rootView!!.findViewById<TextView>(R.id.first_fragment)
        textView.text = mTextStr
    }
}
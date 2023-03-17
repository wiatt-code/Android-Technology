package com.wiatt.simpledemo.myFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.wiatt.simpledemo.R

class FragmentTest3: Fragment() {

    private lateinit var tvShow: TextView
    private var content: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments !=null){
            content = arguments!!.getString(ARG_TEXT).toString()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_test_other, container, false)
        tvShow = rootView.findViewById(R.id.tvShow)
        tvShow.text = content
        return rootView
    }

    companion object {
        private const val ARG_TEXT = "param"
        fun newInstance(param: String): FragmentTest3{
            val fragment = FragmentTest3()
            val args = Bundle()
            args.putString(ARG_TEXT, param)
            fragment.arguments = args
            return fragment
        }
    }
}
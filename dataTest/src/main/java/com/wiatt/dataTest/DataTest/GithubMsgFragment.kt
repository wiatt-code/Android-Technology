package com.wiatt.dataTest.DataTest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wiatt.dataTest.R
import com.wiatt.common.base.BaseFragment
import com.wiatt.dataTest.data.GithubMsg


class GithubMsgFragment: BaseFragment() {

    private lateinit var rvGithubMsg: RecyclerView
    private val rvAdapter by lazy {
        GithubMsgCAdapter(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.fragment_github_message, container, false)
        rvGithubMsg = rootView.findViewById(R.id.rvGithubMsg)
        var layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvGithubMsg.layoutManager = layoutManager
        rvGithubMsg.addItemDecoration(
            DividerItemDecoration(context, layoutManager.orientation)
        )
        rvGithubMsg.adapter = rvAdapter
        return rootView
    }

    fun update(data: MutableList<GithubMsg>) {
        rvAdapter.setData(data)
    }

    companion object {
        fun newInstance(): GithubMsgFragment{
            return GithubMsgFragment()
        }
    }
}
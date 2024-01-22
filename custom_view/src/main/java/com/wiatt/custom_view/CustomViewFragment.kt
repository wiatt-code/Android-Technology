package com.wiatt.custom_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.wiatt.common.base.BaseFragment
import com.wiatt.custom_view.animation.AnimationFragment
import com.wiatt.custom_view.customViewTouchDrag.ViewDragFragment
import com.wiatt.custom_view.dialog.DialogShowFragment
import com.wiatt.custom_view.position_and_size.CusViewFragment
import com.wiatt.custom_view.scalableImage.ScalableImageFragment
import com.wiatt.custom_view.word_measure.WordFragment


@Route(path = "/customView/CustomViewFragment")
class CustomViewFragment : BaseFragment() {

    lateinit var rvList: MyRecyclerView
    lateinit var flContent: FrameLayout

    private var currentFragment: Fragment? = null
    private var infos: MutableList<ViewInfo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_custom_view, container, false)
        rvList = rootView.findViewById(R.id.rvList)
        flContent = rootView.findViewById(R.id.flContent)
        initData()
        initView()
        return rootView
    }
    private fun initData() {
        infos.add(ViewInfo("自定义View", CusViewFragment.newInstance()))
        infos.add(ViewInfo("文字", WordFragment.newInstance()))
        infos.add(ViewInfo("动画", AnimationFragment.newInstance()))
        infos.add(ViewInfo("拖拽", ViewDragFragment.newInstance()))
        infos.add(ViewInfo("手势", ScalableImageFragment.newInstance()))
        infos.add(ViewInfo("弹框", DialogShowFragment.newInstance()))
    }

    private fun initView() {
        val adapter = ViewAdapter(context!!, infos)
        val callback = CustomViewCallback()
        adapter.callback = callback
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvList.adapter = adapter
        rvList.layoutManager = layoutManager
        rvList.addItemDecoration(
            DividerItemDecoration(context, layoutManager.orientation)
        )

        switchFragment(null)
    }



    private fun switchFragment(nextFragment: Fragment?) {
        if(nextFragment == currentFragment) {
            return
        }

        if (currentFragment != null && currentFragment!!.isAdded) {
            activity?.hideFragment(currentFragment!!)
        }

        if (nextFragment != null) {
            if (nextFragment.isAdded) {
                activity?.showFragment(nextFragment)
            } else {
                activity?.addFragment(nextFragment, R.id.flContent)
            }
        }
        currentFragment = nextFragment
    }

    inner class CustomViewCallback(): ViewAdapter.ViewAdapterCallback {
        override fun onClickItem(info: ViewInfo) {
            switchFragment(info.fragment)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = CustomViewFragment()
    }
}
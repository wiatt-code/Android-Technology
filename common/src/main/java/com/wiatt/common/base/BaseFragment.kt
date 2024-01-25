package com.wiatt.common.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

abstract class BaseFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * fragment的扩展函数，用于操作fragment
     */
    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    /**
     * 添加fragment
     */
    fun addFragment(fragment: Fragment, frameId: Int){
        childFragmentManager.inTransaction { add(frameId, fragment) }
    }

    /**
     * 移除fragment
     */
    fun removeFragment(fragment: Fragment){
        childFragmentManager.inTransaction { remove(fragment) }
    }

    /**
     * 展示fragment
     */
    fun showFragment(fragment: Fragment) {
        childFragmentManager.inTransaction{show(fragment)}
    }

    /**
     * 隐藏fragment
     */
    fun hideFragment(fragment: Fragment){
        childFragmentManager.inTransaction { hide(fragment) }
    }

    /**
     * 连接fragment
     */
    fun attachFragment(fragment: Fragment) {
        childFragmentManager.inTransaction{attach(fragment)}
    }

    /**
     * 分离fragment
     */
    fun detachFragment(fragment: Fragment) {
        childFragmentManager.inTransaction{detach(fragment)}
    }
}
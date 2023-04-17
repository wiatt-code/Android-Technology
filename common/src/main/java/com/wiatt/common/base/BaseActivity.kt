package com.wiatt.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity : AppCompatActivity() {

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
     * FragmentActivity.addFragment 表示将addFragment是作为FragmentActivity的扩展函数存在
     * 这样addFragment内部就能直接访问到FragmentActivity中的supportFragmentManager
     */
    fun FragmentActivity.addFragment(fragment: Fragment, frameId: Int){
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }

    /**
     * 移除fragment
     */
    fun FragmentActivity.removeFragment(fragment: Fragment){
        supportFragmentManager.inTransaction { remove(fragment) }
    }

    /**
     * 展示fragment
     */
    fun FragmentActivity.showFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction{show(fragment)}
    }

    /**
     * 隐藏fragment
     */
    fun FragmentActivity.hideFragment(fragment: Fragment){
        supportFragmentManager.inTransaction { hide(fragment) }
    }

    /**
     * 连接fragment
     */
    fun FragmentActivity.attachFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction{attach(fragment)}
    }

    /**
     * 分离fragment
     */
    fun FragmentActivity.detachFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction{detach(fragment)}
    }
}
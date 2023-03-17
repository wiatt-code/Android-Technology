package com.wiatt.simpledemo.myFragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.wiatt.simpledemo.R

class FragmentDemoActivity : AppCompatActivity() {

    private var currentFragment: Fragment? = null
    private lateinit var fragmentTest1: FragmentTest1
    private var fragmentTest2: Fragment = FragmentTest2.newInstance("这是第二个fragment")
    private var fragmentTest3: Fragment = FragmentTest3.newInstance("这是第三个fragment")
    private var fragmentTest4: Fragment = FragmentTest4.newInstance("这是第四个fragment")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_demo)
        /**
         * fragment静态加载中，不能通过findViewById()方法获取对应的fragment
         * 而是通过supportFragmentManager.findFragmentById()获取
         */
        fragmentTest1 = supportFragmentManager.findFragmentById(R.id.fTest1) as FragmentTest1
        fragmentTest1.setMsgSendListener(object : FragmentTest1.OnMsgSendListener {
            override fun fragmentChange(nextFragmentName: String) {
                println("FragmentDemo, nextFragmentName = $nextFragmentName")
                when (nextFragmentName) {
                    FragmentTest2::class.java.name -> switchFragment(fragmentTest2)
                    FragmentTest3::class.java.name -> switchFragment(fragmentTest3)
                    FragmentTest4::class.java.name -> switchFragment(fragmentTest4)
                }
            }
        })
    }

    /**
     * 示例方法，不可用
     */
    fun operateFragment() {
        val fragmentTest2 = FragmentTest2()
        supportFragmentManager  //创建Fragment管理器
                .beginTransaction()   //获取fragment事务对象, 并开启事务
                .add(R.id.llContainer, fragmentTest2)   //调用事务的动态方法动态的添加/替换/移除Fragment
                .commit()   //提交事务

        supportFragmentManager
                .beginTransaction()
                .remove(fragmentTest2)
                .commit()

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.llContainer, fragmentTest2)
                .commit()
    }

    /**
     * 使用replace()替换后会将之前的fragment的view从viewtree中删除
    触发顺序:
    detach()->onPause()->onStop()->onDestroyView()
    attach()->onCreateView()->onActivityCreated()->onStart()->onResume()
     */

    /**
     * 用于切换Fragment
     * 当不显示fragment时，nextFragment为空
     */
    fun switchFragment(nextFragment: Fragment?) {
        if(nextFragment == currentFragment) {
            return
        }

        if (currentFragment != null && currentFragment!!.isAdded) {
            hideFragment(currentFragment!!)
        }

        if (nextFragment != null) {
            if (nextFragment.isAdded) {
                showFragment(nextFragment)
            } else {
                addFragment(nextFragment, R.id.llContainer)
            }
        }
        currentFragment = nextFragment
    }

    /**
     * fragment的扩展函数，用于操作fragment
     */
    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
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
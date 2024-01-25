package com.wiatt.custom_view.fragment_viewpager2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.wiatt.common.base.BaseActivity
import com.wiatt.common.base.BaseFragment
import com.wiatt.custom_view.R
import com.wiatt.custom_view.databinding.ActivityFragmentViewpager2Binding
import java.util.Stack

class FragmentViewpager2Activity : BaseActivity() {

    private lateinit var binding: ActivityFragmentViewpager2Binding

    private var currentFragment: Fragment? = null
    // fragment 任务栈，当前正在显示的fragment保存在顶部
    private var fragmentStack: Stack<BaseFragment> = Stack()
    private lateinit var fragmentType: String

    private var fragmentViewpager2Fragment: FragmentViewpager2Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentViewpager2Binding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        showFragmentViewpager2Page()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (fragmentStack.size <= 1) {
            super.onBackPressed()
        } else {
            fragmentStack.pop()
            val curFragment = fragmentStack.peek()
            switchFragment(curFragment)
        }
    }

    /**
     * 切换到 FragmentViewpager2 页面
     */
    @Synchronized
    private fun showFragmentViewpager2Page() {
        if (fragmentViewpager2Fragment == null) {
            fragmentViewpager2Fragment = FragmentViewpager2Fragment.newInstance()
        }

        if (switchFragment(fragmentViewpager2Fragment)) {
            fragmentType = FRAGMENT_VIEWPAGER2_DETAIL
            fragmentStack.push(fragmentViewpager2Fragment)
        }
    }

    /**
     * 用于切换Fragment
     * 当不显示fragment时，nextFragment为空
     */
    private fun switchFragment(nextFragment: Fragment?): Boolean{
        if(nextFragment == currentFragment) {
            return false
        }

        if (currentFragment != null && currentFragment!!.isAdded) {
            hideFragment(currentFragment!!)
        }

        if (nextFragment != null) {
            if (nextFragment.isAdded) {
                showFragment(nextFragment)
            } else {
                addFragment(nextFragment, R.id.contentFl)
            }
        }
        currentFragment = nextFragment
        return true
    }

    companion object {
        const val MEETING_ROOM_KEY = "meeting_room_key"

        // 会议房间详情页
        const val FRAGMENT_VIEWPAGER2_DETAIL = "fragment_viewpager2_detail"

        /**
         * 启动 fragmentViewpager2 页面
         */
        fun startFragmentViewPager2Page(context: Context) {
            val intent = Intent(context, FragmentViewpager2Activity::class.java)
            intent.putExtra(MEETING_ROOM_KEY, FRAGMENT_VIEWPAGER2_DETAIL)
            context.startActivity(intent)
        }
    }
}
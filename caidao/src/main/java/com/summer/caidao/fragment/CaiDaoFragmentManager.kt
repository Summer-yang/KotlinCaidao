package com.summer.caidao.fragment

import android.content.Context
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/15
 *
 * Description:
 * 单例模式
 * Fragment管理类
 *
 */
class CaiDaoFragmentManager(
        private val mContext: Context,
        private val mContainerId: Int,
        private val mFragmentManager: FragmentManager,
        private val mListener: OnTabChangeListener?) {

    private val tabs = SparseArray<Tab>()
    private var currentTab: Tab? = null

    /**
     * 添加tab
     */
    fun add(menuId: Int, tab: Tab): CaiDaoFragmentManager {
        tabs.put(menuId, tab)
        return this
    }

    /**
     * 获取当前Tab
     */
    fun getCurrentTab(): Tab? {
        return currentTab
    }

    /**
     * 执行点击菜单的操作
     */
    fun performClickMenu(menuId: Int): Boolean {
        val tab = tabs.get(menuId)
        if (tab != null) {
            doSelect(tab)
            return true
        }
        return false
    }

    /**
     * 进行tab的选择操作
     */
    private fun doSelect(tab: Tab) {
        var oldTab: Tab? = null

        if (currentTab != null) {
            oldTab = currentTab
            if (oldTab == tab) {
                //如果当前tab点击的tab，不做任何操作或者刷新
                mListener?.onTabReselected(tab)
                return
            }
        }
        currentTab = tab
        doTabChanged(currentTab, oldTab)
    }

    private fun doTabChanged(newTab: Tab?, oldTab: Tab?) {
        val ft = mFragmentManager.beginTransaction()
        if (null != oldTab) {
            if (null != oldTab.fragment) {
                //从界面移除，但在Fragment的缓存中
                if (null != oldTab.fragment) {
                    ft.detach(oldTab.fragment!!)
                }
            }
        }

        if (newTab != null) {
            if (null == newTab.fragment) {
                val fragment = Fragment.instantiate(mContext, newTab.clx.name, null)
                newTab.fragment = fragment
                ft.add(mContainerId, fragment, newTab.clx.name)
            } else {
                ft.attach(newTab.fragment!!)
            }
        }

        ft.commit()
        notifySelect(newTab, oldTab)
    }

    private fun notifySelect(newTab: Tab?, oldTab: Tab?) {
        mListener?.onTabChange(newTab, oldTab)
    }


    class Tab(val clx: Class<*>, val title: String) {
        //内部缓存对应的Fragment
        var fragment: Fragment? = null
    }

    interface OnTabChangeListener {
        fun onTabReselected(tab: Tab?)
        fun onTabChange(newTab: Tab?, oldTab: Tab?)
    }


}
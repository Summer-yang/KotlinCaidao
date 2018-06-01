package com.summer.caidao.check.click

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/1
 *
 * Description: 防止疯狂连击工具类
 *
 */
class CheckDoubleClick {

    private var lastClickTime: Long = 0
    private var clickSpacingInterval: Long = 1000
    private var lastViewId = -1

    fun isFastDoubleClick(): Boolean {
        return isFastDoubleClick(-1, clickSpacingInterval)
    }

    fun isFastDoubleClick(viewId: Int): Boolean {
        return isFastDoubleClick(viewId, clickSpacingInterval)
    }

    private fun isFastDoubleClick(viewId: Int, spacing_interval: Long): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time.minus(lastClickTime)
        if (lastViewId == viewId && lastClickTime > 0 && timeD < spacing_interval) {
            return true
        }
        lastClickTime = time
        lastViewId = viewId
        return false
    }


}
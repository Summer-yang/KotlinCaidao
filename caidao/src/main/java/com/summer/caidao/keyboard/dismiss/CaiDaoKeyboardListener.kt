package com.summer.caidao.keyboard.dismiss

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/1
 *
 * Description: 键盘状态改变监听
 *
 */
internal interface CaiDaoKeyboardListener {
    fun onKeyboardVisibilityChanged(isVisible: Boolean)
}
package com.summer.caidao.behavior

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/1
 *
 * Description: 自定义Behavior,实现BottomNavigationView滚动出屏幕的效果
 *
 */
class CaiDaoBehaviorBottomNavigationView() : CoordinatorLayout.Behavior<View>() {

    constructor(context: Context, attributeSet: AttributeSet) : this()

    override fun onLayoutChild(parent: CoordinatorLayout?, child: View?, layoutDirection: Int): Boolean {

        if (null != parent && null != child) {
            (child.layoutParams as CoordinatorLayout.LayoutParams).topMargin = parent.measuredHeight - child.measuredHeight
        }

        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {

        //得到依赖View的滑动距离
        if (null != parent && null != child && null != dependency) {

            val offset = ((dependency.layoutParams as CoordinatorLayout.LayoutParams).behavior as AppBarLayout.Behavior).topAndBottomOffset
            child.translationY = -offset.toFloat()
            return false
        }

        return super.onDependentViewChanged(parent, child, dependency)
    }

}
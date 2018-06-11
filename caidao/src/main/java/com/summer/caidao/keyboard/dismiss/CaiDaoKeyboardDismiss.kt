package com.summer.caidao.keyboard.dismiss

import android.app.Activity
import androidx.fragment.app.Fragment
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.ConstraintLayout


/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/1
 *
 * Description: 点击空白位置收起键盘工具类,监听注册类
 *
 */
class CaiDaoKeyboardDismiss {

    private val sSupportedClasses = arrayOf("LinearLayout", "RelativeLayout", "CoordinatorLayout", "ConstraintLayout")

    fun useWith(fragment: Fragment) {
        val viewGroup = fragment.view as ViewGroup
        if (null != fragment.activity) {
            swapMainLayoutWithDismissingLayout(viewGroup, fragment.activity!!)
        }
    }

    fun useWith(activity: Activity) {
        val content = activity.findViewById<FrameLayout>(android.R.id.content)
        val viewGroup = content.getChildAt(0) as ViewGroup

        swapMainLayoutWithDismissingLayout(viewGroup, activity)
    }

    fun useWith(viewGroup: ViewGroup, activity: Activity) {
        swapMainLayoutWithDismissingLayout(viewGroup, activity)
    }

    private fun swapMainLayoutWithDismissingLayout(viewGroup: ViewGroup?, activity: Activity) {
        if (viewGroup == null) {
            return
        }

        var className = ""

        val viewGroupClassName = viewGroup.javaClass.simpleName
        for (name in sSupportedClasses) {
            if (viewGroupClassName == name) {
                className = name
            }
        }

        var generatedLayout: ViewGroup = viewGroup

        when (className) {
            "LinearLayout" -> {
                generatedLayout = CaiDaoKeyboardDismissingLinearLayout(activity)
                generatedLayout.setActivity(activity)
            }
            "RelativeLayout" -> {
                generatedLayout = CaiDaoKeyboardDismissingRelativeLayout(activity)
                generatedLayout.setActivity(activity)
            }
            "CoordinatorLayout" -> {
                generatedLayout = CaiDaoKeyboardDismissingCoordinatorLayout(activity)
                generatedLayout.setActivity(activity)
            }
            "ConstraintLayout" -> {
                generatedLayout = CaiDaoKeyboardDismissingConstraintLayout(activity)
                generatedLayout.setActivity(activity)
            }
        }

        if (className.isEmpty()) {
            return
        }

        if (viewGroup.layoutParams != null) {
            generatedLayout.layoutParams = viewGroup.layoutParams
        } else {
            generatedLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        if (generatedLayout is CaiDaoKeyboardDismissingConstraintLayout) {
            var widthOfOriginalLayout = ConstraintLayout.LayoutParams.MATCH_PARENT
            var heightOfOriginalLayout = ConstraintLayout.LayoutParams.MATCH_PARENT

            if (viewGroup.layoutParams != null) {
                widthOfOriginalLayout = viewGroup.layoutParams.width
                heightOfOriginalLayout = viewGroup.layoutParams.height
            }

            val layoutParams = ConstraintLayout.LayoutParams(widthOfOriginalLayout, heightOfOriginalLayout)
            layoutParams.validate()

            generatedLayout.layoutParams = layoutParams
        }

        while (viewGroup.childCount != 0) {
            val child = viewGroup.getChildAt(0)

            viewGroup.removeViewAt(0)
            generatedLayout.addView(child)
        }

        viewGroup.removeAllViews()
        viewGroup.addView(generatedLayout, 0)
    }

}
package com.summer.caidao.keyboard.dismiss

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout


/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/1
 *
 * Description: 收起键盘工具类
 *
 */
internal class CaiDaoDismissingUtils {

    fun hideKeyboard(activity: Activity) {
        val view = activity.currentFocus
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view == null) {
            return
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun setupKeyboardListener(activity: Activity, keyboardListener: CaiDaoKeyboardListener?) {
        val content = activity.findViewById<FrameLayout>(android.R.id.content)
        val contentView = content.getChildAt(0)

        val view = contentView ?: content

        view.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            view.getWindowVisibleDisplayFrame(r)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - r.bottom

            keyboardListener?.onKeyboardVisibilityChanged(keypadHeight > screenHeight * 0.15)
        }
    }
}
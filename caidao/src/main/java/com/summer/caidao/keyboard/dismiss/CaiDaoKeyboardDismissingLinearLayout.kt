package com.summer.caidao.keyboard.dismiss

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout


/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/1
 *
 * Description:
 *
 */
internal class CaiDaoKeyboardDismissingLinearLayout : LinearLayout, CaiDaoKeyboardListener {

    private var mActivity: Activity? = null
    private var mIsKeyboardVisible: Boolean = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private fun setupKeyboardListener() {
        if (null == mActivity) {
            throw Exception("please set activity first")
        }

        CaiDaoDismissingUtils().setupKeyboardListener(mActivity!!, this)
    }

    fun setActivity(activity: Activity) {
        mActivity = activity
        setupKeyboardListener()
    }

    override fun onKeyboardVisibilityChanged(isVisible: Boolean) {
        mIsKeyboardVisible = isVisible
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (null == mActivity) {
            throw Exception("please set activity first")
        }

        val wasDispatched = super.dispatchTouchEvent(ev)
        if (!wasDispatched && mIsKeyboardVisible) {
            CaiDaoDismissingUtils().hideKeyboard(mActivity!!)
        }
        return wasDispatched
    }

}
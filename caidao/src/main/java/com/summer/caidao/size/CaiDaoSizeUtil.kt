package com.summer.caidao.size

import android.content.Context

/**
 * Created by
 * User -> Summer
 * Date -> 2018/7/16
 *
 * Description:
 *
 */
object CaiDaoSizeUtil {

    fun dx2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return dpValue.times(scale).plus(0.5f).toInt()
    }

    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return pxValue.div(scale).plus(0.5f).toInt()
    }

}
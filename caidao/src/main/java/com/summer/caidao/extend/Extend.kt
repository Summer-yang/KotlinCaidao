package com.summer.caidao.extend

import android.view.View

/**
 * 给View设置ClickListener扩展方法
 */
fun View.OnClickListener.setClicker(vararg view: View) {
    view.forEach { it.setOnClickListener(this) }
}

/**
 * AppCompatActivity的扩展属性
 * 代表setResult的失败情况
 */
val RESULT_ERROR: Int
    get() = -2

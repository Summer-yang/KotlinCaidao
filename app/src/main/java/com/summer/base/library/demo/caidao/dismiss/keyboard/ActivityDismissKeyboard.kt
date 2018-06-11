package com.summer.base.library.demo.caidao.dismiss.keyboard

import android.os.Bundle
import com.summer.base.library.R
import com.summer.base.library.base.BaseActivity
import com.summer.caidao.keyboard.dismiss.CaiDaoKeyboardDismiss

class ActivityDismissKeyboard : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dismiss_keyboard)

        CaiDaoKeyboardDismiss().useWith(this)
    }
}

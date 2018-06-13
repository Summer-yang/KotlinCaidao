package com.summer.base.library.demo.caidao.dismiss.keyboard

import android.os.Bundle
import com.summer.base.library.R
import com.summer.base.library.base.BaseActivity
import com.summer.caidao.keyboard.dismiss.CaiDaoKeyboardDismiss

class ActivityDismissKeyboard : BaseActivity() {

    override fun getDataFromLastView(bundle: Bundle?) {

    }

    override fun getLayout(): Int {
        return R.layout.activity_dismiss_keyboard
    }

    override fun initView() {
        CaiDaoKeyboardDismiss().useWith(this)
    }
}

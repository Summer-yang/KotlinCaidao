package com.summer.base.library.login.wechat

import android.os.Bundle
import com.summer.base.library.BaseActivity
import com.summer.base.library.R
import kotlinx.android.synthetic.main.activity_login_we_chat.*

class ActivityLoginWeChat : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_we_chat)

        btnWeChatLogin.setOnClickListener {
            weChatLogin()
        }
    }

    private fun weChatLogin() {

    }
}

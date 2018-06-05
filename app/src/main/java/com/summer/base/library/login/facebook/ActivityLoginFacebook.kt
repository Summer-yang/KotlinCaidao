package com.summer.base.library.login.facebook

import android.os.Bundle
import com.summer.base.library.BaseActivity
import com.summer.base.library.R
import kotlinx.android.synthetic.main.activity_login_facebook.*

/**
 * Facebook登录
 *
 * 配置步骤
 *
 *
 */
class ActivityLoginFacebook : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_facebook)

        btnFacebookLogin.setOnClickListener {
            login()
        }

        btnFacebookLogout.setOnClickListener {
            logout()
        }
    }

    private fun login() {

    }

    private fun logout() {

    }
}

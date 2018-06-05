package com.summer.base.library.login.sina

import android.content.Intent
import android.os.Bundle
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.auth.WbConnectErrorMessage
import com.sina.weibo.sdk.auth.sso.SsoHandler
import com.summer.base.library.BaseActivity
import com.summer.base.library.Constants
import com.summer.base.library.R
import com.summer.caidao.toast.CaidaoToast
import kotlinx.android.synthetic.main.activity_login_sian.*

class ActivityLoginSian : BaseActivity(), WbAuthListener {

    private var mSsoHandler: SsoHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_sian)

        btnSian.setOnClickListener {
            sinaLogin()
        }
    }

    private fun sinaLogin() {
        val mAuthInfo = AuthInfo(this,
                Constants.SINA_WEIBO_APP_KEY,
                Constants.SINA_WEIBO_REDIRECT_URL,
                Constants.SINA_WEIBO_SCOPE)

        WbSdk.install(this, mAuthInfo)

        mSsoHandler = SsoHandler(this)
        mSsoHandler?.authorize(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mSsoHandler?.authorizeCallBack(requestCode, resultCode, data)
    }

    override fun onSuccess(p0: Oauth2AccessToken?) {
        if (null != p0) {
            if (p0.isSessionValid) {
                // 获取到token
                updateUI(p0.token)
            } else {
                CaidaoToast.Builder(this).build().showShortSafe("isSessionValid为false")
            }
        }
    }

    override fun onFailure(p0: WbConnectErrorMessage?) {
        if (null != p0) {
            CaidaoToast.Builder(this).build().showShortSafe(String.format("%s:%s", p0.errorCode, p0.errorMessage))
        }
    }

    override fun cancel() {
        CaidaoToast.Builder(this).build().showShortSafe("取消登录")
    }

    private fun updateUI(mAccessToken: String) {
        accessToken.text = mAccessToken
    }

}

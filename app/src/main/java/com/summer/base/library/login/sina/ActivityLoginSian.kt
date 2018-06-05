package com.summer.base.library.login.sina

import android.content.Intent
import android.os.Bundle
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.*
import com.sina.weibo.sdk.auth.sso.SsoHandler
import com.summer.base.library.BaseActivity
import com.summer.base.library.Constants
import com.summer.base.library.R
import com.summer.caidao.toast.CaidaoToast
import kotlinx.android.synthetic.main.activity_login_sian.*

class ActivityLoginSian : BaseActivity(), WbAuthListener {

    private var mIsSessionValid = false
    private var mAccessToken: Oauth2AccessToken? = null
    private var mSsoHandler: SsoHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_sian)

        // 判定是否登陆过
        mAccessToken = AccessTokenKeeper.readAccessToken(this)
        mIsSessionValid = if (null != mAccessToken) {
            mAccessToken!!.isSessionValid
        } else {
            false
        }

        if (mIsSessionValid) {
            btnSian.text = "登出"
            accessToken.text = if (null != mAccessToken) {
                mAccessToken!!.token
            } else {
                "mAccessToken null"
            }
        }

        btnSian.setOnClickListener {
            if (mIsSessionValid) {
                signOut()
            } else {
                sinaLogin()
            }
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
        mIsSessionValid = true

        btnSian.text = "登出"
        accessToken.text = mAccessToken
    }


    private fun signOut() {
        AccessTokenKeeper.clear(applicationContext)
        mAccessToken = Oauth2AccessToken()

        btnSian.text = "新浪微博登录"
        accessToken.text = "这里显示新浪微博返回的AccessToken"
    }
}

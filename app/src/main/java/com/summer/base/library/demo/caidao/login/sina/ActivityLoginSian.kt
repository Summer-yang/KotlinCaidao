package com.summer.base.library.demo.caidao.login.sina

import android.content.Intent
import android.os.Bundle
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.*
import com.sina.weibo.sdk.auth.sso.SsoHandler
import com.summer.base.library.base.BaseActivity
import com.summer.base.library.base.Constants
import com.summer.base.library.R
import com.summer.caidao.toast.CaidaoToast
import kotlinx.android.synthetic.main.activity_login_sian.*

/**
 * 新浪微博登录
 * 配置步骤
 * 1.在新浪后台申请App,配置相关信息
 * 2.在工程根目录build.gradle中添加
 *   maven { url "https://dl.bintray.com/thelasterstar/maven/" }
 * 3.在app module下添加
 *   implementation 'com.sina.weibo.sdk:core:4.2.7:openDefaultRelease@aar'
 *
 *   splits {
 *           abi {
 *               enable true
 *               reset()
 *               include 'arm64-v8a', 'armeabi', 'armeabi-v7a', 'mips', 'mips64', 'x86', 'x86_64'
 *               universalApk true
 *               }
 *           }
 *
 * 4.在libs里添加所有so文件
 * 5.在常量文件里配置
 *   SINA_WEIBO_APP_KEY
 *   SINA_WEIBO_REDIRECT_URL(与新浪后台配置的地址一致,建议使用官方默认地址)
 *   SINA_WEIBO_SCOPE
 */
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
            btnSianLogin.text = "登出"
            accessToken.text = if (null != mAccessToken) {
                mAccessToken!!.token
            } else {
                "mAccessToken null"
            }
        }

        btnSianLogin.setOnClickListener {
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

        btnSianLogin.text = "登出"
        accessToken.text = mAccessToken
    }


    private fun signOut() {
        AccessTokenKeeper.clear(applicationContext)
        mAccessToken = Oauth2AccessToken()

        mIsSessionValid = false
        btnSianLogin.text = "新浪微博登录"
        accessToken.text = "这里显示新浪微博返回的AccessToken"
    }
}

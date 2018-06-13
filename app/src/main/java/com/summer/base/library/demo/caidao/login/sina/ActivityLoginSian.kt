package com.summer.base.library.demo.caidao.login.sina

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.*
import com.sina.weibo.sdk.auth.sso.SsoHandler
import com.summer.base.library.R
import com.summer.base.library.base.BaseActivity
import com.summer.base.library.base.Constants
import com.summer.caidao.extend.RESULT_ERROR

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

    private lateinit var mAccessToken: Oauth2AccessToken
    private lateinit var mSsoHandler: SsoHandler

    override fun getDataFromLastView(bundle: Bundle?) {

    }

    override fun getLayout(): Int {
        return R.layout.activity_login_sian
    }

    override fun initView() {
        // 判定是否登陆过
        mAccessToken = AccessTokenKeeper.readAccessToken(applicationContext)

        if (mAccessToken.isSessionValid) {
            setResult(mAccessToken.token)
        } else {
            sinaLogin()
        }
    }

    private fun sinaLogin() {

        WbSdk.install(this, AuthInfo(this,
                Constants.SINA_WEIBO_APP_KEY,
                Constants.SINA_WEIBO_REDIRECT_URL,
                Constants.SINA_WEIBO_SCOPE))

        mSsoHandler = SsoHandler(this)
        mSsoHandler.authorize(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mSsoHandler.authorizeCallBack(requestCode, resultCode, data)
    }

    override fun onSuccess(p0: Oauth2AccessToken?) {
        if (null == p0) {
            setResult(RESULT_ERROR)
        } else {
            if (p0.isSessionValid) {
                // 获取到token
                setResult(p0.token)
            } else {
                setResult(RESULT_ERROR)
                finish()
            }
        }
    }

    override fun onFailure(p0: WbConnectErrorMessage?) {
        setResult(RESULT_ERROR)
        finish()
    }

    override fun cancel() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun setResult(token: String) {
        val bundle = Bundle()
        bundle.putString("token", token)
        setResult(Activity.RESULT_OK, Intent().putExtras(bundle))
        finish()
    }

    override fun onBackPressed() {

    }
}

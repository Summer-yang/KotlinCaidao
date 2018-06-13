package com.summer.base.library.demo.caidao.login.facebook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.summer.base.library.R
import com.summer.base.library.base.BaseActivity
import com.summer.caidao.extend.RESULT_ERROR

/**
 * Facebook登录
 *
 * 配置步骤
 * 1.在Facebook开发者后天配置相关信息
 * 2.添加如下依赖,排除Facebook的support依赖
 * implementation ('com.facebook.android:facebook-android-sdk:4.31.0'){
 *      exclude group: "com.android.support"
 * }
 * 3.获取开发你要散列
 * keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore | openssl sha1 -binary | openssl base64
 * 4.获取发布迷药散列
 * keytool -exportcert -alias YOUR_RELEASE_KEY_ALIAS -keystore YOUR_RELEASE_KEY_PATH | openssl sha1 -binary | openssl base64
 * 5.在strings文件中配置
 * facebook_app_id
 * fb_login_protocol_scheme
 * 6.配置AndroidManifest.xml文件
 * 7.您可以通过检查AccessToken.getCurrentAccessToken() 和 Profile.getCurrentProfile() 来查看用户是否已登录。
 *
 */
class ActivityLoginFacebook : BaseActivity() {

    private lateinit var callbackManager: CallbackManager

    override fun getDataFromLastView(bundle: Bundle?) {

    }

    override fun getLayout(): Int {
        return R.layout.activity_login_facebook
    }

    override fun initView() {
        // 检查是否登陆过
        val accessToken = AccessToken.getCurrentAccessToken()
        if (null != accessToken && !accessToken.isExpired) {
            loginSuccess(accessToken.token)
        } else {
            login()
        }

    }

    private fun login() {
        callbackManager = CallbackManager.Factory.create()
        val loginManager = LoginManager.getInstance()
        loginManager.logInWithReadPermissions(this, listOf("public_profile"))
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                if (null == result) {
                    setResult(RESULT_ERROR)
                    finish()
                } else {
                    loginSuccess(result.accessToken.token)
                }
            }

            override fun onCancel() {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }

            override fun onError(error: FacebookException?) {
                setResult(RESULT_ERROR)
                finish()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun loginSuccess(token: String) {
        val bundle = Bundle()
        bundle.putString("token", token)
        setResult(Activity.RESULT_OK, Intent().putExtras(bundle))
        finish()
    }
}

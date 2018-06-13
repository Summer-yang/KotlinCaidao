package com.summer.base.library.demo.caidao.login.google

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.summer.base.library.R
import com.summer.base.library.base.BaseActivity
import com.summer.caidao.extend.RESULT_ERROR

/**
 * Google+登录
 * 配置步骤
 * 1.在Firebase上注册App
 *
 * 2.获取debug SHA 证书指纹
 * keytool -exportcert -list -v \-alias androiddebugkey -keystore ~/.android/debug.keystore
 *
 * 3.获取release SHA 证书指纹
 * keytool -exportcert -list -v \-alias <your-key-name> -keystore <path-to-production-keystore>
 *
 * 证书指纹添加在Firebase后台项目设置中
 *
 * 4.在工程根目录build.gradle中添加
 *   classpath 'com.google.gms:google-services:4.0.0'
 *
 * 5.在app module下添加
 *   implementation 'com.google.firebase:firebase-core:16.0.0'
 *   implementation 'com.google.android.gms:play-services-auth:15.0.1'
 *
 * 6.在strings文件或常量文件中定义GOOGLE_KEY
 *
 * 7.使用以下代码登录
 */
const val REQUEST_CODE_GOOGLE_SIGN_IN = 900

class ActivityLoginGoogle : BaseActivity() {

    override fun getDataFromLastView(bundle: Bundle?) {

    }

    override fun getLayout(): Int {
        return R.layout.activity_login_google
    }

    override fun initView() {
        // 判定是不是登陆过
        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (null != account && !account.isExpired) {
            val bundle = Bundle()
            bundle.putString("token", account.idToken)
            setResult(Activity.RESULT_OK, Intent().putExtras(bundle))
            finish()
        } else {
            googleLogin()
        }
    }

    private fun googleLogin() {
        // Configure sign-in to request the user's ID, email address, and basic profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("277496732900-5e8ontlq8or9s7rksbakrq00afagopsk.apps.googleusercontent.com")
                .requestEmail()
                .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        startActivityForResult(googleSignInClient.signInIntent, REQUEST_CODE_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (REQUEST_CODE_GOOGLE_SIGN_IN == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val bundle = Bundle()
                    bundle.putString("token", account.idToken)
                    setResult(Activity.RESULT_OK, Intent().putExtras(bundle))
                } catch (e: ApiException) {
                    setResult(RESULT_ERROR)
                }
            } else {
                setResult(RESULT_ERROR)
            }
        } else {
            setResult(RESULT_ERROR)
        }
        finish()
    }
}

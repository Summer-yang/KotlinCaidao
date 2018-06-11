package com.summer.base.library.demo.caidao.login.google

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.summer.base.library.base.BaseActivity
import com.summer.base.library.base.Constants
import com.summer.base.library.R
import com.summer.caidao.toast.CaidaoToast
import kotlinx.android.synthetic.main.activity_login_google.*

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
class ActivityLoginGoogle : BaseActivity() {

    private val requestCodeGoogleSignIn = 999
    private var isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_google)

        // 判定是不是登陆过
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (null != account && !account.isExpired) {
            updateUI(account.idToken)
        }

        btnGoogleLogin.setOnClickListener {
            if (isLoggedIn) {
                CaidaoToast.Builder(this).build().showShortSafe("已经登陆过了")
            } else {
                googleLogin()
            }
        }

        btnGoogleLogout.setOnClickListener {
            if (isLoggedIn) {
                googleLogout()
            } else {
                CaidaoToast.Builder(this).build().showShortSafe("请先登录")
            }
        }
    }

    fun googleLogin() {
        // Configure sign-in to request the user's ID, email address, and basic profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Constants.GOOGLE_KEY)
                .requestEmail()
                .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        startActivityForResult(googleSignInClient.signInIntent, requestCodeGoogleSignIn)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCodeGoogleSignIn == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    updateUI(account.idToken)
                } catch (e: ApiException) {
                    token.text = String.format("%s:%s", "登录失败task.getResult异常", e.message)
                }
            } else {
                token.text = "登录失败 resultCode != Activity.RESULT_OK"
            }
        }
    }


    private fun updateUI(idToken: String?) {
        if (idToken.isNullOrBlank()) {
            isLoggedIn = false
            token.text = "token null"
        } else {
            isLoggedIn = true
            token.text = idToken
        }
    }

    private fun googleLogout() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Constants.GOOGLE_KEY)
                .requestEmail()
                .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut()

        isLoggedIn = false
        token.text = "这里显示Google登录返回的token"
    }
}

package com.summer.base.library.login.google

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.summer.base.library.BaseActivity
import com.summer.base.library.Constants
import com.summer.base.library.R
import kotlinx.android.synthetic.main.activity_login_google.*

/**
 * Google+登录
 * 配置步骤
 * 1.在Firebase上注册App
 * 2.在工程根目录build.gradle中添加
 *   classpath 'com.google.gms:google-services:4.0.0'
 * 3.在app module下添加
 *   implementation 'com.google.firebase:firebase-core:16.0.0'
 *   implementation 'com.google.android.gms:play-services-auth:15.0.1'
 * 4.在strings文件或常量文件中定义GOOGLE_KEY
 * 5.使用以下代码登录
 */
class ActivityLoginGoogle : BaseActivity() {

    private val requestCodeGoogleSignIn = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_google)


        btnGoogleLogin.setOnClickListener {
            googleLogin()
        }
    }

    fun googleLogin() {

        // 之前登陆过
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (null != account) {
            updateUI(account.idToken)
        } else {
            // Configure sign-in to request the user's ID, email address, and basic profile. ID and basic profile are included in DEFAULT_SIGN_IN.
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(Constants.GOOGLE_KEY)
                    .requestEmail()
                    .build()

            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            startActivityForResult(googleSignInClient.signInIntent, requestCodeGoogleSignIn)

        }
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
                    updateUI(null)
                }
            } else {
                token.text = "登录异常"
            }
        }
    }


    private fun updateUI(idToken: String?) {
        if (idToken.isNullOrBlank()) {
            token.text = "登录异常"
        } else {
            token.text = idToken
        }
    }
}
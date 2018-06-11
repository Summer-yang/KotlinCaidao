package com.summer.base.library.demo.caidao.login.wechat

import android.os.Bundle
import com.summer.base.library.R
import com.summer.base.library.base.BaseActivity
import com.tencent.mm.opensdk.openapi.IWXAPI
import kotlinx.android.synthetic.main.activity_login_we_chat.*

/**
 * 微信登录
 * 配置步骤:
 * 引入依赖
 * compile 'com.tencent.mm.opensdk:wechat-sdk-android-with-mta:+'
 * 或
 * compile 'com.tencent.mm.opensdk:wechat-sdk-android-without-mta:+'
 * 前者包含统计功能
 *
 * 需要的权限
 * <uses-permission android:name="android.permission.INTERNET"/>
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
 * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 *
 * 在常量文件里定义APP_ID
 *
 * 请求步骤:
 * 1.向微信终端注册你的id
 *
 */
class ActivityLoginWeChat : BaseActivity() {

    private lateinit var api: IWXAPI

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

package com.summer.base.library.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.summer.base.library.base.Constants
import com.summer.base.library.demo.caidao.login.wechat.ActivityLoginWeChat
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory


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
class WXEntryActivity : Activity(), IWXAPIEventHandler {

    private lateinit var api: IWXAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        api = WXAPIFactory.createWXAPI(this, Constants.WE_CHAT_APP_ID, true)
        api.handleIntent(intent, this)
    }

    override fun onResp(p0: BaseResp?) {
        when (p0?.errCode) {
            BaseResp.ErrCode.ERR_OK -> if (p0 is SendAuth.Resp) {
                setResult(BaseResp.ErrCode.ERR_OK, p0.code)
            }
            BaseResp.ErrCode.ERR_COMM -> {
                setResult(BaseResp.ErrCode.ERR_COMM, "ERR_COMM")
            }
            BaseResp.ErrCode.ERR_USER_CANCEL -> {
                setResult(BaseResp.ErrCode.ERR_USER_CANCEL, "ERR_USER_CANCEL")
            }
            BaseResp.ErrCode.ERR_SENT_FAILED -> {
                setResult(BaseResp.ErrCode.ERR_SENT_FAILED, "ERR_SENT_FAILED")
            }
            BaseResp.ErrCode.ERR_AUTH_DENIED -> {
                setResult(BaseResp.ErrCode.ERR_AUTH_DENIED, "ERR_AUTH_DENIED")
            }
            BaseResp.ErrCode.ERR_UNSUPPORT -> {
                setResult(BaseResp.ErrCode.ERR_UNSUPPORT, "ERR_UNSUPPORT")
            }
            BaseResp.ErrCode.ERR_BAN -> {
                setResult(BaseResp.ErrCode.ERR_BAN, "ERR_BAN")
            }
        }
    }

    override fun onReq(p0: BaseReq?) {}


    private fun setResult(resultCode: Int, result: String) {
        val intent = Intent(this, ActivityLoginWeChat::class.java)
        val bundle = Bundle()
        bundle.putInt("resultCode", resultCode)
        bundle.putString("result", result)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

}

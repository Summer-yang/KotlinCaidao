package com.summer.base.library.demo.caidao.login.wechat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.summer.base.library.R
import com.summer.base.library.base.BaseActivity
import com.summer.base.library.base.Constants
import com.summer.base.library.http.ApiClient
import com.summer.base.library.http.ApiErrorModel
import com.summer.base.library.http.NetworkScheduler
import com.summer.base.library.http.ThirdApiResponse
import com.summer.caidao.entity.CaiDaoEntityGetWeChatLoginToken
import com.summer.caidao.extend.RESULT_ERROR
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.kotlin.bindUntilEvent


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
 * 注意事项:
 * 1.WXEntryActivity类一定要放到在微信后台注册的包名下的wxapi包下,名称也不要改
 * 2.此Activity设置 launchMode="singleTask" 在onNewIntent中接收WXEntryActivity传递的参数
 *   传参方式为startActivity方式,所以设置成singleTask模式
 *
 * 3.编写新项目时替换Constants文件里的WE_CHAT_APP_ID和WE_CHAT_APP_SECRET
 * 4.WXEntryActivity要在AndroidManifest文件中声明
 *
 */
class ActivityLoginWeChat : BaseActivity() {

    private lateinit var api: IWXAPI

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (null != intent) {
            val bundle = intent.extras
            if (null != bundle) {

                val resultCode = bundle.getInt("resultCode", -100)

                when (resultCode) {
                    BaseResp.ErrCode.ERR_OK -> {
                        getToken(bundle.getString("result"))
                    }
                    BaseResp.ErrCode.ERR_USER_CANCEL -> {
                        setResult(Activity.RESULT_CANCELED)
                        finish()
                    }
                    else -> {
                        setResult(RESULT_ERROR)
                        finish()
                    }
                }
            }
        }
    }

    override fun getDataFromLastView(bundle: Bundle?) {

    }

    override fun getLayout(): Int {
        return R.layout.activity_login_we_chat
    }

    override fun initView() {
        api = WXAPIFactory.createWXAPI(this, Constants.WE_CHAT_APP_ID, true)
        if (api.registerApp(Constants.WE_CHAT_APP_ID)) {
            requestCode()
        } else {
            setResult(RESULT_ERROR)
            finish()
        }
    }

    /**
     * 拉起微信获取临时票据(code)
     */
    private fun requestCode() {
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = ""
        api.sendReq(req)
    }


    private fun getToken(code: String) {
        ApiClient.instance.service.getYahooForStarList(Constants.WE_CHAT_APP_ID, Constants.WE_CHAT_APP_SECRET, code)
                .compose(NetworkScheduler.compose())
                .bindUntilEvent(this, ActivityEvent.DESTROY)
                .subscribe(object : ThirdApiResponse<CaiDaoEntityGetWeChatLoginToken>(this) {
                    override fun success(data: CaiDaoEntityGetWeChatLoginToken) {
                        val resultBundle = Bundle()
                        resultBundle.putString("token", data.access_token)
                        setResult(Activity.RESULT_OK, Intent().putExtras(resultBundle))
                        finish()
                    }

                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        setResult(RESULT_ERROR)
                        finish()
                    }
                })
    }
}

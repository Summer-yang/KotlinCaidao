package com.summer.base.library.demo.caidao.share.sina

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.api.*
import com.sina.weibo.sdk.auth.*
import com.sina.weibo.sdk.auth.sso.SsoHandler
import com.sina.weibo.sdk.share.WbShareCallback
import com.sina.weibo.sdk.share.WbShareHandler
import com.summer.base.library.R
import com.summer.base.library.base.BaseActivity
import com.summer.base.library.base.Constants
import com.summer.caidao.toast.CaidaoToast
import kotlinx.android.synthetic.main.activity_share_sina.*
import java.io.File

/**
 * 新浪微博分享
 *
 * 1.分享方式为原生分享
 * 2.注意事项
 *   a.分享多图和视频必须要有文件读写权限
 */
class ActivityShareSina : BaseActivity(), WbShareCallback, WbAuthListener {

    // 判定是否登录过如果没登陆过则先登录用
    private lateinit var mAccessToken: Oauth2AccessToken
    private lateinit var mSsoHandler: SsoHandler
    private var mIsSessionValid = false

    private lateinit var shareHandler: WbShareHandler

    // 要分享的内容
    private lateinit var entityShareWeibo: EntityShareWeibo

    // 普通分享 or 分享到story
    private var isNormalShare = true

    override fun getDataFromLastView(bundle: Bundle?) {

    }

    override fun getLayout(): Int {
        return R.layout.activity_share_sina
    }

    override fun initView() {
        val mAuthInfo = AuthInfo(this,
                Constants.SINA_WEIBO_APP_KEY,
                Constants.SINA_WEIBO_REDIRECT_URL,
                Constants.SINA_WEIBO_SCOPE)
        // 必须先执行此方法
        WbSdk.install(this, mAuthInfo)

        // 获取要分享的内容
        entityShareWeibo = getShareData()

        shareHandler = WbShareHandler(this)
        shareHandler.registerApp()

        // 初始化
        mAccessToken = AccessTokenKeeper.readAccessToken(this)
        mIsSessionValid = mAccessToken.isSessionValid

        initShare()
    }

    private fun initShare() {
        btnShareText.setOnClickListener {
            isNormalShare = true

            if (mIsSessionValid) {
                normalShare()
            } else {
                sinaLogin()
            }
        }

        btnShareToStory.setOnClickListener {
            isNormalShare = false

            if (mIsSessionValid) {
                storyShare()
            } else {
                sinaLogin()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        shareHandler.doResultIntent(intent, this)
    }

    /**
     * 获取数据
     */
    private fun getShareData(): EntityShareWeibo {
        val entityShareWeibo = EntityShareWeibo()
        entityShareWeibo.shareMessage = "分享到微博的信息"
        return entityShareWeibo
    }

    private fun normalShare() {

        if (null == entityShareWeibo.shareMessage
                && null == entityShareWeibo.shareImagePath
                && null == entityShareWeibo.shareMultiImagePaths
                && null == entityShareWeibo.shareVideo) {
            CaidaoToast.Builder(this).build().showShortSafe("请选择要分享的内容")
            return
        }

        if ((null != entityShareWeibo.shareMultiImagePaths ||
                        null != entityShareWeibo.shareVideo) && !WbSdk.supportMultiImage(this)) {
            CaidaoToast.Builder(this).build().showShortSafe("微博客户端版本过低,不支持多图和视频分享")
            return
        }

        val weiboMultiMessage = WeiboMultiMessage()

        if (null != entityShareWeibo.shareMessage) {
            val textObject = TextObject()
            textObject.text = entityShareWeibo.shareMessage
            weiboMultiMessage.textObject = textObject
        }

        if (null != entityShareWeibo.shareImagePath) {
            val imageObject = ImageObject()
            imageObject.imagePath = entityShareWeibo.shareImagePath
            weiboMultiMessage.imageObject = imageObject
        }

        // 多图和视频需要做判定
        if (null != entityShareWeibo.shareMultiImagePaths) {
            val multiImageObject = MultiImageObject()
            multiImageObject.imageList = controlImagePath(entityShareWeibo.shareMultiImagePaths!!)
            weiboMultiMessage.multiImageObject = multiImageObject
        }

        if (null != entityShareWeibo.shareVideo) {
            val videoSourceObject = VideoSourceObject()
            videoSourceObject.videoPath = Uri.fromFile(File(entityShareWeibo.shareVideo))
            weiboMultiMessage.videoSourceObject = videoSourceObject
        }

        if (null != entityShareWeibo.shareWebPagePath) {
            val webpageObject = WebpageObject()
            webpageObject.defaultText = entityShareWeibo.shareWebPagePath
        }


        shareHandler.shareMessage(weiboMultiMessage, false)
    }

    /**
     * 分享到微博故事
     * 微博故事支持单张图片和视频(不能共存)
     */
    private fun storyShare() {

        if (null == entityShareWeibo.shareImagePath && null == entityShareWeibo.shareVideo) {
            CaidaoToast.Builder(this).build().showShortSafe("请选择要分享的图片或视频")
            return
        }

        if (null != entityShareWeibo.shareImagePath && null != entityShareWeibo.shareVideo) {
            CaidaoToast.Builder(this).build().showShortSafe("微博故事不能同时存在图片和视频")
            return
        }

        val storyMessage = StoryMessage()
        if (null != entityShareWeibo.shareImagePath) {
            storyMessage.imageUri = Uri.fromFile(File(entityShareWeibo.shareImagePath))
        }

        if (null != entityShareWeibo.shareVideo) {
            storyMessage.videoUri = Uri.fromFile(File(entityShareWeibo.shareVideo))
        }

        shareHandler.shareToStory(storyMessage)
    }

    override fun onWbShareFail() {
        CaidaoToast.Builder(this).build().showShortSafe("分享失败")
    }

    override fun onWbShareCancel() {
        CaidaoToast.Builder(this).build().showShortSafe("分享取消")
    }

    override fun onWbShareSuccess() {
        CaidaoToast.Builder(this).build().showShortSafe("分享成功")
    }

    private fun controlImagePath(imagePaths: ArrayList<String>): ArrayList<Uri> {
        val imageUris = ArrayList<Uri>()
        imagePaths.forEach {
            imageUris.add(Uri.fromFile(File(it)))
        }
        return imageUris
    }

    /**--------- 授权  ----*/

    private fun sinaLogin() {
        mSsoHandler = SsoHandler(this)
        mSsoHandler.authorize(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mSsoHandler.authorizeCallBack(requestCode, resultCode, data)
    }

    override fun onSuccess(p0: Oauth2AccessToken?) {
        mIsSessionValid = true

        if (isNormalShare) {
            normalShare()
        } else {
            storyShare()
        }
    }

    override fun onFailure(p0: WbConnectErrorMessage?) {
        mIsSessionValid = false
        CaidaoToast.Builder(this).build().showShortSafe("分享失败,账户授权失败")
    }

    override fun cancel() {
        mIsSessionValid = false
        CaidaoToast.Builder(this).build().showShortSafe("分享失败,账户授权取消")
    }
    /**--------- 授权  ----*/

}

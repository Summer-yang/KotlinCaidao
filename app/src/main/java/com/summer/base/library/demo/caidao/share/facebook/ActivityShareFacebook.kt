package com.summer.base.library.demo.caidao.share.facebook

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.*
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.summer.base.library.R
import com.summer.base.library.base.BaseActivity
import com.summer.caidao.toast.CaidaoToast
import kotlinx.android.synthetic.main.activity_share_facebook.*


/**
 * Facebook分享
 * 文档地址
 * https://developers.facebook.com/docs/sharing/android?sdk=maven
 *
 * 步骤:
 * 1.添加依赖
 *  implementation('com.facebook.android:facebook-android-sdk:4.31.0') {
 *       exclude group: "com.android.support"
 *  }
 * 2.在开发者网站注册App
 * 3.在strings文件中添加facebook_app_id
 * 4.在AndroidManifest文件中添加
 *  <meta-data
 *      android:name="com.facebook.sdk.ApplicationId"
 *      android:value="@string/facebook_app_id" />
 *  <provider
 *      android:name="com.facebook.FacebookContentProvider"
 *      android:authorities="com.facebook.app.FacebookContentProvider接app id"
 *      android:exported="true" />
 *
 *  <!-- 禁用自动事件记录 -->
 *  <meta-data
 *      android:name="com.facebook.sdk.AutoLogAppEventsEnabled"
 *      android:value="false" />
 *
 *  <!-- Facebook登录 -->
 *
 *  <activity
 *      android:name="com.facebook.FacebookActivity"
 *      android:configChanges="keyboard|keyboardHidden|screenLayout|screenSize|orientation"
 *      android:label="@string/app_name" />
 *  <activity
 *      android:name="com.facebook.CustomTabActivity"
 *      android:exported="true">
 *      <intent-filter>
 *           <action android:name="android.intent.action.VIEW" />
 *           <category android:name="android.intent.category.DEFAULT" />
 *           <category android:name="android.intent.category.BROWSABLE" />
 *           <data android:scheme="@string/fb_login_protocol_scheme" />
 *      </intent-filter>
 *  </activity>
 *
 *  <!-- Facebook登录 -->
 *
 * 注意:
 * 1.实施分享时，应用不应预填写任何分享内容，否则将违反 Facebook 开放平台政策
 * 2.如果您的应用分享包含 Google Play 或 App Store 中任何应用的链接，则该分享中包含的描述和图片将被忽略。
 *   相反，我们将直接从应用商店中为该应用获取标题和图片（如果没有图片可获取，则该分享将不包含图片）。
 * 3.如果要分享照片,用户需要安装版本 7.0 或更高版本的原生 Android 版 Facebook 应用。
 * 4.同时分享图片和视频
 *   用户需要安装版本 71 或以上的原生 Android 版 Facebook 应用。
 *   用户每次可以分享最多包含 6 个照片和视频元素的内容。
 */
class ActivityShareFacebook : BaseActivity(), FacebookCallback<Sharer.Result> {

    private lateinit var callbackManager: CallbackManager
    private lateinit var shareDialog: ShareDialog

    override fun getDataFromLastView(bundle: Bundle?) {

    }

    override fun getLayout(): Int {
        return R.layout.activity_share_facebook
    }

    override fun initView() {
        callbackManager = CallbackManager.Factory.create()
        shareDialog = ShareDialog(this)

        btnShareLinkContent.setOnClickListener {
            shareLinkContent()
        }

        btnShareImage.setOnClickListener {
            shareImage()
        }
    }

    private fun shareLinkContent() {
        val content = ShareLinkContent.Builder().setContentUrl(Uri.parse("https://developers.facebook.com")).build()
        if (ShareDialog.canShow(ShareLinkContent::class.java)) {
            shareDialog.registerCallback(callbackManager, this)
            shareDialog.show(content)
        }
    }

    private fun shareImage() {
        val image = BitmapFactory.decodeStream(assets.open("icon_512.png"))
        val photo = SharePhoto.Builder().setBitmap(image).build()
        val content = SharePhotoContent.Builder().addPhoto(photo).build()
        if (ShareDialog.canShow(SharePhotoContent::class.java)) {
            shareDialog.registerCallback(callbackManager, this)
            shareDialog.show(content)
        }
    }

    private fun shareVideo(videoFileUri: Uri) {
        val shareVideo = ShareVideo.Builder().setLocalUrl(videoFileUri).build()
        val content = ShareVideoContent.Builder().setVideo(shareVideo).build()
    }

    private fun shareImageAndVideo(images: List<String>, videos: List<String>) {
        val shareMediaContent = ShareMediaContent.Builder()
        images.forEach {
            shareMediaContent.addMedium(SharePhoto.Builder().setImageUrl(Uri.parse(it)).build())
        }

        videos.forEach {
            shareMediaContent.addMedium(ShareVideo.Builder().setLocalUrl(Uri.parse(it)).build())
        }

        val shareDialog = ShareDialog(this)
        shareDialog.registerCallback(callbackManager, this)
        if (ShareDialog.canShow(ShareLinkContent::class.java)) {
            shareDialog.show(shareMediaContent.build(), ShareDialog.Mode.AUTOMATIC)
        }
    }

    override fun onSuccess(result: Sharer.Result?) {
        CaidaoToast.Builder(this).build().showShortSafe("分享成功")
    }

    override fun onCancel() {
        CaidaoToast.Builder(this).build().showShortSafe("取消分享")
    }

    override fun onError(error: FacebookException?) {
        CaidaoToast.Builder(this).build().showShortSafe(String.format("%s:%s", "分享失败", error.toString()))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}

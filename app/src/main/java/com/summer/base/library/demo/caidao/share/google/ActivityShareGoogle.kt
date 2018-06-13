package com.summer.base.library.demo.caidao.share.google

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import com.google.android.gms.plus.PlusShare
import com.summer.base.library.R
import com.summer.base.library.base.BaseActivity
import kotlinx.android.synthetic.main.activity_share_google.*


/**
 * Google+ 分享
 * 1.首先引入
 * implementation 'com.google.android.gms:play-services-plus:15.0.1'
 *
 *
 */
class ActivityShareGoogle : BaseActivity() {

    private val REQ_SELECT_PHOTO = 1
    private val REQ_START_SHARE = 2

    override fun getDataFromLastView(bundle: Bundle?) {

    }

    override fun getLayout(): Int {
        return R.layout.activity_share_google
    }

    override fun initView() {
        val imagePath = MediaStore.Images.Media.insertImage(contentResolver,
                BitmapFactory.decodeStream(assets.open("icon_512.png")),
                "icon_512",
                "BaseLibrary")

        btnBasicShareGoogle.setOnClickListener {
            basicShareOnGooglePlus()
        }

        btnShareInteractive.setOnClickListener {
            shareInteractiveOnGooglePlus()
        }
        btnShareImageOrVideo.setOnClickListener {
            shareImageOrVideoOnGooglePlus()
        }
    }

    /**
     * 分享文本与连接
     * You can mention specific people in the prefilled text by adding a plus sign (+) followed by their Google+ user ID or their email address
     */
    private fun basicShareOnGooglePlus() {
        val shareIntent = PlusShare.Builder(this)
                .setType("text/plain")
                .setText("Heard about this restaurant from +ysx1988071@gmail.com #nomnomnom")
                .setContentUrl(Uri.parse("https://developers.google.com/+/"))
                .intent
        startActivityForResult(shareIntent, 0)

    }

    /**
     * 分享交互式帖子
     */
    private fun shareInteractiveOnGooglePlus() {
        val builder = PlusShare.Builder(this)
        // Set call-to-action metadata.
        builder.addCallToAction(
                "CREATE_ITEM",
                /** call-to-action button label */
                Uri.parse("http://plus.google.com/pages/create"),
                /** call-to-action url (for desktop use) */
                "/pages/create"
                /** call to action deep-link ID (for mobile use), 512 characters or fewer */)

        // Set the content url (for desktop use).
        builder.setContentUrl(Uri.parse("https://plus.google.com/pages/"))

        // Set the target deep-link ID (for mobile use).
        builder.setContentDeepLinkId("/pages/",
                null, null, null)

        // Set the share text.
        builder.setText("Create your Google+ Page too!")

        startActivityForResult(builder.intent, 0)

    }

    /**
     * When you share media to Google+, you cannot also use the setContentUrl method.
     * If you want to include a URL in the post with the media,
     * you should append the URL to the prefilled text in the setText() method.
     */
    private fun shareImageOrVideoOnGooglePlus() {
        // 在相册里选择多媒体资源
        val photoPicker = Intent(Intent.ACTION_PICK)
        photoPicker.type = "video/*, image/*"
        startActivityForResult(photoPicker, REQ_SELECT_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_SELECT_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                val selectedImage = intent.data
                val cr = this.contentResolver
                val mime = cr.getType(selectedImage)

                val share = PlusShare.Builder(this)
                share.setText("hello everyone!")
                share.addStream(selectedImage)
                share.setType(mime)
                startActivityForResult(share.intent, REQ_START_SHARE)
            }
        }

    }

}

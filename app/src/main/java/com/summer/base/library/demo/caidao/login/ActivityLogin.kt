package com.summer.base.library.demo.caidao.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.summer.base.library.R
import com.summer.base.library.base.BaseActivity
import com.summer.base.library.demo.caidao.login.facebook.ActivityLoginFacebook
import com.summer.base.library.demo.caidao.login.google.ActivityLoginGoogle
import com.summer.base.library.demo.caidao.login.sina.ActivityLoginSian
import com.summer.base.library.demo.caidao.login.wechat.ActivityLoginWeChat
import com.summer.caidao.extend.setClicker
import com.summer.caidao.keyboard.dismiss.CaiDaoKeyboardDismiss
import com.summer.caidao.toast.CaidaoToast
import kotlinx.android.synthetic.main.activity_login.*

const val REQUEST_CODE_WE_CHAT = 900
const val REQUEST_CODE_SINA = 901
const val REQUEST_CODE_GOOGLE = 902
const val REQUEST_CODE_FACEBOOK = 903

class ActivityLogin : BaseActivity(), View.OnClickListener {

    override fun getDataFromLastView(bundle: Bundle?) {

    }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        CaiDaoKeyboardDismiss().useWith(this)

        setClicker(btnSignIn, tvSignUp, tvFindPwd, ivWeChat, ivSina, ivGoogle, ivFacebook)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnSignIn -> {

            }
            R.id.tvSignUp -> {

            }
            R.id.tvFindPwd -> {

            }
            R.id.ivWeChat -> {
                startActivityForResult(Intent(this, ActivityLoginWeChat::class.java), REQUEST_CODE_WE_CHAT)
            }
            R.id.ivSina -> {
                startActivityForResult(Intent(this, ActivityLoginSian::class.java), REQUEST_CODE_SINA)
            }
            R.id.ivGoogle -> {
                startActivityForResult(Intent(this, ActivityLoginGoogle::class.java), REQUEST_CODE_GOOGLE)
            }
            R.id.ivFacebook -> {
                startActivityForResult(Intent(this, ActivityLoginFacebook::class.java), REQUEST_CODE_FACEBOOK)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val token: String? = data?.extras?.getString("token")

                if (token.isNullOrBlank()) {
                    CaidaoToast.Builder(this).build().showShort("token is null")
                } else {
                    when (requestCode) {
                        REQUEST_CODE_WE_CHAT -> {
                            CaidaoToast.Builder(this).build().showShort(token!!)
                        }
                        REQUEST_CODE_SINA -> {
                            CaidaoToast.Builder(this).build().showShort(token!!)
                        }
                        REQUEST_CODE_GOOGLE -> {
                            CaidaoToast.Builder(this).build().showShort(token!!)
                        }
                        REQUEST_CODE_FACEBOOK -> {
                            CaidaoToast.Builder(this).build().showShort(token!!)
                        }
                    }
                }
            }
            Activity.RESULT_CANCELED -> CaidaoToast.Builder(this).build().showShort("取消登录")
            else -> CaidaoToast.Builder(this).build().showShort("登录失败")
        }
    }
}

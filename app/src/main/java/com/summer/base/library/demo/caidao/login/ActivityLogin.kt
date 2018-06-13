package com.summer.base.library.demo.caidao.login

import android.os.Bundle
import android.view.View
import com.summer.base.library.R
import com.summer.base.library.base.BaseActivity
import com.summer.base.library.demo.caidao.login.sina.ActivityLoginSian
import com.summer.caidao.extend.setClicker
import com.summer.caidao.keyboard.dismiss.CaiDaoKeyboardDismiss
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

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

            }
            R.id.ivSina -> {
                startActivity<ActivityLoginSian>()
            }
            R.id.ivGoogle -> {

            }
            R.id.ivFacebook -> {


            }
        }
    }
}

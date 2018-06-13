package com.summer.base.library

import android.content.Intent
import android.os.Bundle
import com.summer.base.library.base.BaseActivity

class ActivityLauncher : BaseActivity() {
    override fun getDataFromLastView(bundle: Bundle?) {
        
    }

    override fun getLayout(): Int {
        return R.layout.activity_launcher
    }

    override fun initView() {
        startActivity(Intent(this, ActivityMain::class.java))
        finish()
    }
}

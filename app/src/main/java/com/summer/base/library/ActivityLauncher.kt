package com.summer.base.library

import android.content.Intent
import android.os.Bundle
import com.summer.base.library.base.BaseActivity

class ActivityLauncher : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        startActivity(Intent(this, ActivityMain::class.java))

        finish()
    }
}

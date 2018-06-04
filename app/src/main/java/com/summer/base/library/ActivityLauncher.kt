package com.summer.base.library

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ActivityLauncher : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        startActivity(Intent(this, ActivityMain::class.java))

        finish()
    }
}

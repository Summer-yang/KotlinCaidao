package com.summer.base.library.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/4
 *
 * Description:
 *
 */
@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDataFromLastView(intent.extras)
        setContentView(getLayout())
        initView()

    }

    abstract fun getDataFromLastView(bundle: Bundle?)
    abstract fun getLayout(): Int
    abstract fun initView()

}
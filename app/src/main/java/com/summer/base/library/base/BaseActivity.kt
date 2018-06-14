package com.summer.base.library.base

import android.annotation.SuppressLint
import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/4
 *
 * Description:
 *
 */
@SuppressLint("Registered")
abstract class BaseActivity : RxAppCompatActivity() {

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
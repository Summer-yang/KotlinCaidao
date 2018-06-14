package com.summer.base.library.base

import androidx.multidex.MultiDexApplication
import com.summer.base.library.http.ApiClient

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/5
 *
 * Description:
 *
 */
class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // 初始化网络请求类
        ApiClient.instance.init()
    }

}
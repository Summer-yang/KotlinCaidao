package com.summer.base.library.http

import android.os.Build
import android.util.Log
import com.summer.base.library.BuildConfig
import okhttp3.Headers
import okhttp3.Interceptor

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/15
 *
 * Description:
 *
 */
class HttpHeaderInterceptor {

    /**
     * 修改Http请求头
     */
    fun getHeaderInterceptor(): Interceptor {
        return Interceptor {

            val headers = Headers.Builder()
                    .add("Content-Type", "application/json; charset=UTF-8")
                    .add("User-Agent",
                            "Android/"
                                    + "Package:"
                                    + BuildConfig.APPLICATION_ID
                                    + "-"
                                    + "Version Name:"
                                    + BuildConfig.VERSION_NAME
                                    + "-"
                                    + "Phone Info:"
                                    + Build.PRODUCT
                                    + "("
                                    + Build.VERSION.RELEASE
                                    + ")")
                    .build()

            val request = it.request().newBuilder().headers(headers).build()

            if (BuildConfig.DEBUG) {
                Log.d("Http Headers", request.toString())
            }

            it.proceed(request)
        }
    }

}
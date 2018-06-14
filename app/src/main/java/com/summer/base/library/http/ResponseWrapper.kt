package com.summer.base.library.http

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/14
 *
 * Description:
 *
 */
data class ResponseWrapper<T>(
        var error_code: Int,
        var error_message: String,
        var data: T
)
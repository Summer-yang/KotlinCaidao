package com.summer.base.library.http

import android.content.Context
import androidx.annotation.StringRes
import com.summer.base.library.R

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/14
 *
 * Description:
 *
 */
private const val DEFAULT_CODE = 1

enum class ApiErrorType(val code: Int,
                        @param: StringRes private val messageId: Int) {
    INTERNAL_SERVER_ERROR(500, R.string.service_error),
    BAD_GATEWAY(502, R.string.service_error),
    NOT_FOUND(404, R.string.not_found),
    CONNECTION_TIMEOUT(408, R.string.timeout),
    NETWORK_NOT_CONNECT(499, R.string.network_wrong),
    UNEXPECTED_ERROR(700, R.string.unexpected_error);

    fun getApiErrorModel(context: Context): ApiErrorModel {
        return ApiErrorModel(DEFAULT_CODE, context.getString(messageId))
    }
}
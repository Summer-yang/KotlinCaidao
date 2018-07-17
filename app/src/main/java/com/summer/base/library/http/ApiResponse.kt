package com.summer.base.library.http

import android.content.Context
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/14
 *
 * Description:
 *
 */
abstract class ApiResponse<T>(private val context: Context) : Observer<ResponseWrapper<T>> {

    abstract fun success(data: T)
    abstract fun failure(statusCode: Int, apiErrorModel: ApiErrorModel)

    private object Status {
        const val SUCCESS = 200
    }

    override fun onSubscribe(d: Disposable) {
//        CaiDaoDialog.show(context)
    }

    override fun onNext(t: ResponseWrapper<T>) {
        if (t.error_code == Status.SUCCESS) {
            success(t.data)
            return
        }

        val apiErrorModel: ApiErrorModel = when (t.error_code) {
            ApiErrorType.INTERNAL_SERVER_ERROR.code ->
                ApiErrorType.INTERNAL_SERVER_ERROR.getApiErrorModel(context)
            ApiErrorType.BAD_GATEWAY.code ->
                ApiErrorType.BAD_GATEWAY.getApiErrorModel(context)
            ApiErrorType.NOT_FOUND.code ->
                ApiErrorType.NOT_FOUND.getApiErrorModel(context)
            else -> ApiErrorModel(t.error_code, t.error_message)
        }
        failure(t.error_code, apiErrorModel)
    }

    override fun onError(e: Throwable) {
//        CaiDaoDialog.cancel()
        val apiErrorType: ApiErrorType = when (e) {
            is UnknownHostException -> ApiErrorType.NETWORK_NOT_CONNECT
            is ConnectException -> ApiErrorType.NETWORK_NOT_CONNECT
            is SocketTimeoutException -> ApiErrorType.CONNECTION_TIMEOUT
            else -> ApiErrorType.UNEXPECTED_ERROR
        }
        failure(apiErrorType.code, apiErrorType.getApiErrorModel(context))
    }

    override fun onComplete() {
//        CaiDaoDialog.cancel()
    }
}
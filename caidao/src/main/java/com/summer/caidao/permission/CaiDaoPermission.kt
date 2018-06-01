package com.summer.caidao.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.PermissionChecker

/**
 * Created by
 * User -> Summer
 * Date -> 2018/6/1
 *
 * Description: 运行时权限工具类
 *
 */
class CaiDaoPermission {

    /**
     * 回调接口
     */
    interface OnPermissionResultListener {
        fun onSuccess()

        fun onShouldShow(shouldShowPermission: List<String>)

        fun onFailed()
    }

    /**
     * 检测权限
     */
    fun checkPermissionsInActivity(context: Activity, request_permission_code: Int, vararg checkPermissions: String): Boolean {
        // 如果为空则返回true
        if (checkPermissions.isEmpty()) return true

        var isHas = true
        val permissions = ArrayList<String>()
        checkPermissions.forEach {
            // 如果要检查的权限没有被许可
            if (PermissionChecker.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED) {
                isHas = false
                permissions.add(it)
            }
        }

        if (!isHas) {
            ActivityCompat.requestPermissions(context, permissions.toArray(arrayOf()), request_permission_code)
        }
        return isHas
    }

    /**
     * 在onRequestPermissionsResult中调用处理权限检测结果
     */
    fun onPermissionResult(context: Activity, permissions: Array<out String>, grantResults: IntArray, listener: OnPermissionResultListener) {

        val positions = ArrayList<String>()
        grantResults.forEachIndexed { index, i ->
            if (i != PackageManager.PERMISSION_GRANTED) {
                positions.add(permissions[index])
            }
        }

        if (0 == positions.size) {
            listener.onSuccess()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[0])) {
                listener.onShouldShow(positions)
            } else {
                listener.onFailed()
            }
        }

    }
}
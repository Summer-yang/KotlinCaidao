package com.summer.caidao.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment

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
     * 检测权限Activity中使用
     */
    fun checkPermissionsInActivity(context: Activity, request_permission_code: Int, vararg checkPermissions: String): Boolean {
        // 如果是小于6.0版本则永远返回true
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true

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
     * 检测权限Fragment中使用
     */
    fun checkPermissionInFragment(fragment: Fragment, request_permission_code: Int, vararg checkPermissions: String): Boolean {
        // 如果是小于6.0版本则永远返回true
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true

        // 如果为空则返回true
        if (checkPermissions.isEmpty()) return true

        var isHas = true
        val permissions = ArrayList<String>()
        checkPermissions.forEach {
            // 如果要检查的权限没有被许可
            if (PermissionChecker.checkSelfPermission(fragment.context!!, it) != PackageManager.PERMISSION_GRANTED) {
                isHas = false
                permissions.add(it)
            }
        }
        if (!isHas) {
            fragment.requestPermissions(permissions.toArray(arrayOf()), request_permission_code)
        }
        return isHas
    }

    /**
     * 在onRequestPermissionsResult中调用处理权限检测结果
     */
    fun onPermissionResultInActivity(activity: Activity, permissions: Array<out String>, grantResults: IntArray, listener: OnPermissionResultListener) {

        val positions = ArrayList<String>()
        grantResults.forEachIndexed { index, i ->
            if (i != PackageManager.PERMISSION_GRANTED) {
                positions.add(permissions[index])
            }
        }

        if (0 == positions.size) {
            listener.onSuccess()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])) {
                listener.onShouldShow(positions)
            } else {
                listener.onFailed()
            }
        }
    }

    /**
     * 在onRequestPermissionsResult中调用处理权限检测结果
     */
    fun onPermissionResultInFragment(fragment: Fragment, permissions: Array<out String>, grantResults: IntArray, listener: OnPermissionResultListener) {
        val positions = ArrayList<String>()
        grantResults.forEachIndexed { index, i ->
            if (i != PackageManager.PERMISSION_GRANTED) {
                positions.add(permissions[index])
            }
        }

        if (0 == positions.size) {
            listener.onSuccess()
        } else {
            if (fragment.shouldShowRequestPermissionRationale(permissions[0])) {
                listener.onShouldShow(positions)
            } else {
                listener.onFailed()
            }
        }
    }
}
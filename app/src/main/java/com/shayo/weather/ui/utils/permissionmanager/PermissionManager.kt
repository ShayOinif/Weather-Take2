package com.shayo.weather.ui.utils.permissionmanager

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

interface PermissionManager {
    fun registerRequest(
        newPermissionRequest: PermissionRequest
    )

    fun checkPermissions()

    fun unRegisterRequest()
}

class PermissionRequest(
    appCompatActivity: AppCompatActivity,
    val permissions: List<String>,
    @StringRes
    val permissionDisplayResId: Int,
    @StringRes
    val permissionRationaleResId: Int,
    @StringRes
    val noPermissionRationale: Int,
    callBack: (Map<String, Boolean>) -> Unit
) {
    val appCompatActivity = WeakReference(appCompatActivity)
    val callBack= WeakReference(callBack)
}
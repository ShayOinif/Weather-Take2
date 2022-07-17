package com.shayo.weather.ui.utils.permissionmanager

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.shayo.weather.R
import java.lang.ref.WeakReference
import javax.inject.Inject

class PermissionManagerImpl @Inject constructor() : PermissionManager {
    private var permissionRequest: PermissionRequest? = null
    private var permissionResult: MutableMap<String, Boolean>? = null
    private var permissionCheck: ActivityResultLauncher<Array<String>>? = null
    private var isRationale = false

    override fun registerRequest(newPermissionRequest: PermissionRequest) {
        permissionRequest = newPermissionRequest
        isRationale = false

        permissionRequest?.apply {
            appCompatActivity.get()?.let { currentAppCompatActivity ->

                permissionResult = permissions.associateWith { false }.toMutableMap()

                permissionCheck =
                    currentAppCompatActivity.registerForActivityResult(
                        ActivityResultContracts.RequestMultiplePermissions()
                    ) { grantResults ->

                        if (grantResults.containsValue(false)) {
                            if (isRationale)
                                currentAppCompatActivity.showNoPermissionsDialog(
                                    grantResults
                                )
                        } else {
                            sendResult(
                                callBack,
                                grantResults
                            )
                        }
                    }
            }
        }
    }

    override fun checkPermissions() {
        permissionRequest?.apply {
            appCompatActivity.get()?.let { currentAppCompatActivity ->

                when {
                    currentAppCompatActivity.areAllPermissionsGranted() ->
                        sendPositiveResult()
                    currentAppCompatActivity.shouldShowPermissionRationale(permissions) -> {
                        isRationale = true
                        currentAppCompatActivity.displayRationale()
                    }
                    else -> {
                        permissionCheck?.launch(
                            permissions.toTypedArray()
                        )
                    }
                }
            }
        }
    }

    override fun unRegisterRequest() {
        permissionRequest = null
        permissionResult = null
        permissionCheck = null
    }

    private fun AppCompatActivity.displayRationale() {
        permissionRequest?.apply {
            AlertDialog.Builder(this@displayRationale)
                .setTitle(getString(R.string.dialog_permission_request_title))
                .setMessage(
                    getString(
                        R.string.permission_rationale,
                        getString(permissionDisplayResId),
                        getString(permissionRationaleResId),
                    )
                )
                .setCancelable(false)
                .setPositiveButton(getString(R.string.dialog_permission_button_positive)) { _, _ ->
                    permissionCheck?.launch(
                        permissions.toTypedArray()
                    )
                }
                .setNegativeButton(getString(R.string.dialog_permission_button_negative)) { _, _ ->
                    sendResult(callBack, permissionResult)
                }
                .show()
        }
    }

    private fun sendPositiveResult() {
        permissionRequest?.apply {
            callBack.get()?.invoke(
                permissions.associateWith { true }
            )
        }
    }

    private fun sendResult(
        callback: WeakReference<(Map<String, Boolean>) -> Unit>,
        grantResults: Map<String, Boolean>?,
    ) {
        grantResults?.run {
            callback.get()?.invoke(
                grantResults
            )
        }
    }

    private fun AppCompatActivity.areAllPermissionsGranted(): Boolean {
        var areAllGranted = true

        permissionResult?.let { currentPermissionResult ->
            currentPermissionResult.forEach { (permission, _) ->
                if (hasPermission(permission)) {
                    currentPermissionResult[permission] = true
                } else {
                    areAllGranted = false
                }
            }
        }

        return areAllGranted
    }

    private fun AppCompatActivity.shouldShowPermissionRationale(permissions: List<String>) =
        permissions.any {
            shouldShowRequestPermissionRationale(it)
        }

    private fun AppCompatActivity.hasPermission(permission: String) =
        ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED

    private fun AppCompatActivity.showNoPermissionsDialog(
        grantResults: Map<String, Boolean>
    ) {
        permissionRequest?.let { currentPermissionRequest ->
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_no_permission_title))
                .setMessage(getString(currentPermissionRequest.noPermissionRationale))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.dialog_permission_button_positive)) { _, _ ->
                    sendResult(currentPermissionRequest.callBack, grantResults)
                }
                .show()
        }
    }
}


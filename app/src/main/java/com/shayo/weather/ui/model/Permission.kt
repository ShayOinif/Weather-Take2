package com.shayo.weather.ui.model

import android.Manifest

sealed class Permission private constructor(
    val name: String,
) {

    object CoarseLocation : Permission(
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    companion object {
        fun systemToPermission(systemName: String) : Permission? =
            when (systemName) {
                Manifest.permission.ACCESS_COARSE_LOCATION -> CoarseLocation
                else -> null
            }
    }
}
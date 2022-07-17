package com.shayo.weather.ui.main

import android.Manifest
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shayo.weather.R
import com.shayo.weather.di.MainNavigator
import com.shayo.weather.ui.utils.navigator.Navigator
import com.shayo.weather.ui.utils.navigator.NavigatorParams
import com.shayo.weather.ui.utils.permissionmanager.PermissionManager
import com.shayo.weather.ui.utils.permissionmanager.PermissionRequest
import com.shayo.weather.utils.gps.GpsChecker
import com.shayo.weather.utils.networkstatuschecker.NetworkStatusChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val networkStatusChecker: NetworkStatusChecker,
    private val gpsChecker: GpsChecker,
    @MainNavigator
    private val navigator: Navigator,
    private val permissionManager: PermissionManager
) : ViewModel() {

    val networkStatus = networkStatusChecker.hasInternet

    val gpsStatus = gpsChecker.hasGps

    fun registerRequest(
        mainActivity: MainActivity
    ) {
        permissionManager.registerRequest(PermissionRequest(
            mainActivity,
            listOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            R.string.coarse_location_permission,
            R.string.coarse_location_rationale,
            R.string.coarse_location_no_permission_rationale,
        ) {
            Log.d(
                "Shay",
                if (it.containsValue(false))
                    "no permissions"
                else
                    "has permission"
            )
        }
        )
    }

    fun checkPermissions() {
        permissionManager.checkPermissions()
    }

    fun unRegisterRequest() {
        permissionManager.unRegisterRequest()
    }

    fun registerNetworkListener() =
        viewModelScope.launch {
            networkStatusChecker.registerListener()
        }


    fun unRegisterNetworkListener() =
        viewModelScope.launch {
            networkStatusChecker.unRegisterListener()
        }

    fun registerGpsListener() =
        viewModelScope.launch {
            gpsChecker.registerListener()
        }


    fun unRegisterGpsListener() =
        viewModelScope.launch {
            gpsChecker.unRegisterListener()
        }

    fun resolveNavigation(navigationParams: NavigatorParams) {
        navigator.resolveNavigation(navigationParams)
    }
}
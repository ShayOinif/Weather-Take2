package com.shayo.weather.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shayo.weather.utils.gps.GpsChecker
import com.shayo.weather.utils.networkstatuschecker.NetworkStatusChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val networkStatusChecker: NetworkStatusChecker,
    private val gpsChecker: GpsChecker,
) : ViewModel() {
    private val _permissionStatus = MutableStateFlow(PermissionStatus.CHECKING)
    val permissionStatus: StateFlow<PermissionStatus>
    get() = _permissionStatus

    val gpsStatus: StateFlow<Boolean>
        get() = gpsChecker.hasGps

    val networkStatus: StateFlow<Boolean>
    get() = networkStatusChecker.hasInternet

    fun updatePermissionStatus(permissionStatus: PermissionStatus) =
        viewModelScope.launch {
            _permissionStatus.emit(permissionStatus)
        }

    fun registerNetworkMonitor() {
        networkStatusChecker.registerNetworkMonitor()
    }

    fun unregisterNetworkMonitor() {
        networkStatusChecker.unregisterNetworkMonitor()
    }
}

enum class PermissionStatus {
    CHECKING,
    GRANTED,
    DENIED
}
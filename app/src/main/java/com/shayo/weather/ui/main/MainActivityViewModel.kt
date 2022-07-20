package com.shayo.weather.ui.main

import androidx.lifecycle.ViewModel
import com.shayo.weather.utils.gps.GpsChecker
import com.shayo.weather.utils.networkstatuschecker.NetworkStatusChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val networkStatusChecker: NetworkStatusChecker,
    private val gpsChecker: GpsChecker,
) : ViewModel() {

    val gpsStatus: StateFlow<Boolean>
        get() = gpsChecker.hasGps

    val networkStatus: StateFlow<Boolean>
    get() = networkStatusChecker.hasInternet

    fun registerNetworkMonitor() {
        networkStatusChecker.registerNetworkMonitor()
    }

    fun unregisterNetworkMonitor() {
        networkStatusChecker.unregisterNetworkMonitor()
    }
}
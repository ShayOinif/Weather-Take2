package com.shayo.weather.utils.networkstatuschecker

import kotlinx.coroutines.flow.StateFlow

interface NetworkStatusChecker {

    val hasInternet: StateFlow<Boolean>

    fun registerNetworkMonitor()

    fun unregisterNetworkMonitor()
}
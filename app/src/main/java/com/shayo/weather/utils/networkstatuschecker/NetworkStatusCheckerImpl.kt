package com.shayo.weather.utils.networkstatuschecker

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.shayo.weather.utils.externalcoroutine.ExternalCoroutine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkStatusCheckerImpl @Inject constructor(
    private val externalCoroutine: ExternalCoroutine,
    private val connectivityManager: ConnectivityManager,
) :
    NetworkStatusChecker {

    private val _hasInternet = MutableStateFlow(hasInternetConnection())
    override val hasInternet: StateFlow<Boolean>
        get() = _hasInternet

    override fun refreshStatus() {
        externalCoroutine.launch {
            _hasInternet.emit(hasInternetConnection())
        }
    }

    private fun hasInternetConnection(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false

        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
    }
}
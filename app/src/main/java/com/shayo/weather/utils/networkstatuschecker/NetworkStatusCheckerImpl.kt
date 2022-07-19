package com.shayo.weather.utils.networkstatuschecker

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.shayo.weather.utils.externalcoroutine.ExternalCoroutine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkStatusCheckerImpl @Inject constructor(
    private val externalCoroutine: ExternalCoroutine,
    private val connectivityManager: ConnectivityManager,
) : NetworkStatusChecker {

    private val _hasInternet = MutableStateFlow(false)
    override val hasInternet: StateFlow<Boolean>
        get() = _hasInternet


    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    override fun registerNetworkMonitor() {

        val networkRequest = createNetworkRequest()
        networkCallback = createNetworkCallback().apply {
            connectivityManager.registerNetworkCallback(
                networkRequest,
                this
            )
        }
    }

    override fun unregisterNetworkMonitor() {
        networkCallback?.apply {
            connectivityManager.unregisterNetworkCallback(this)

            networkCallback = null
        }
    }

    private fun createNetworkRequest() =
    NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private fun createNetworkCallback():ConnectivityManager.NetworkCallback  = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            externalCoroutine.launch {
                _hasInternet.emit(hasInternetConnection())
            }
        }

        // Network capabilities have changed for the network
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)

            externalCoroutine.launch {
                _hasInternet.emit(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
            }
        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)

            externalCoroutine.launch {
                _hasInternet.emit(hasInternetConnection())
            }
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
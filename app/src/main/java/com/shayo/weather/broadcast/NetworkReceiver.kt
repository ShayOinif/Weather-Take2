package com.shayo.weather.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.shayo.weather.utils.externalcoroutine.ExternalCoroutine
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkReceiver @Inject constructor(
    private val externalCoroutineScope: ExternalCoroutine,
    private val connectivityManager: ConnectivityManager,
    @ApplicationContext appContext: Context,
) : BroadcastReceiver() {

    private val _hasInternet = MutableSharedFlow<Boolean>(1)

    val hasInternet: SharedFlow<Boolean>
        get() = _hasInternet

    init {
        externalCoroutineScope.launch {

            _hasInternet.emit(hasInternetConnection())
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action?.equals("android.net.conn.CONNECTIVITY_CHANGE") == true) {

            externalCoroutineScope.launch {
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
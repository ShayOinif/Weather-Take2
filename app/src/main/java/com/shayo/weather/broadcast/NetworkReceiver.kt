package com.shayo.weather.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import com.shayo.weather.utils.networkstatuschecker.NetworkStatusChecker
import javax.inject.Inject

class NetworkReceiver @Inject constructor(
    private val networkStatusChecker: NetworkStatusChecker
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action?.equals(CONNECTIVITY_ACTION) == true) {
            networkStatusChecker.refreshStatus()
        }
    }
}
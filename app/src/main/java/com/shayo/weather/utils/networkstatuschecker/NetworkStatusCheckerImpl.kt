package com.shayo.weather.utils.networkstatuschecker

import android.content.Context
import android.content.IntentFilter
import com.shayo.weather.broadcast.NetworkReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class NetworkStatusCheckerImpl @Inject constructor(
    private val networkReceiver: NetworkReceiver,
    @ApplicationContext
    private val appContext: Context,
) :
    NetworkStatusChecker {

    override val hasInternet: SharedFlow<Boolean>
        get() = networkReceiver.hasInternet

    override fun registerListener() {
        IntentFilter("android.net.conn.CONNECTIVITY_CHANGE").also {
            appContext.registerReceiver(networkReceiver, it)
        }
    }

    override fun unRegisterListener() {
        appContext.unregisterReceiver(networkReceiver)
    }
}
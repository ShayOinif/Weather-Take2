package com.shayo.weather.utils.gps

import android.content.Context
import android.content.IntentFilter
import android.location.LocationManager
import com.shayo.weather.broadcast.GpsReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GpsCheckerImpl @Inject constructor(
    private val gpsReceiver: GpsReceiver,
    @ApplicationContext
    private val appContext: Context,
) : GpsChecker {

    override val hasGps
    get() = gpsReceiver.gpsStatus

    override fun registerListener() {
        IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION).also {
            appContext.registerReceiver(gpsReceiver, it)
        }
    }

    override fun unRegisterListener() {
        appContext.unregisterReceiver(gpsReceiver)
    }
}
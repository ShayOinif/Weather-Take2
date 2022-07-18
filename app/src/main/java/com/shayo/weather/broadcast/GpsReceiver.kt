package com.shayo.weather.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import com.shayo.weather.utils.gps.GpsChecker
import javax.inject.Inject

class GpsReceiver @Inject constructor(
    private val gpsChecker: GpsChecker,
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action?.equals(LocationManager.MODE_CHANGED_ACTION) == true) {
            gpsChecker.refreshStatus()
        }
    }
}
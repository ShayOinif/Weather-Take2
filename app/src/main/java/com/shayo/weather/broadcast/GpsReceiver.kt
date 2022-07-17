package com.shayo.weather.broadcast

import android.content.BroadcastReceiver
import kotlinx.coroutines.flow.SharedFlow

abstract class GpsReceiver : BroadcastReceiver() {
    abstract val gpsStatus: SharedFlow<Boolean>
}
package com.shayo.weather.broadcast

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.shayo.weather.utils.externalcoroutine.ExternalCoroutine
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

class GpsReceiverImpl @Inject constructor(
    private val externalCoroutineScope: ExternalCoroutine,
    @ApplicationContext appContext: Context,
) : GpsReceiver() {

    private val _gpsStatus = MutableSharedFlow<Boolean>(1)

    override val gpsStatus: SharedFlow<Boolean>
        get() = _gpsStatus

    // TODO :Change to di
    private val locationManager = ContextCompat.getSystemService(
        appContext,
        LocationManager::class.java
    ) as LocationManager

    init {
        externalCoroutineScope.launch {
            _gpsStatus.emit(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action?.equals("android.location.PROVIDERS_CHANGED") == true) {

            externalCoroutineScope.launch {
                val currentStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                if (_gpsStatus.last() != currentStatus)
                    _gpsStatus.emit(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            }
        }
    }
}
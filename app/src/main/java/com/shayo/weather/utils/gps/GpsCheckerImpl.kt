package com.shayo.weather.utils.gps

import android.location.LocationManager
import com.shayo.weather.utils.externalcoroutine.ExternalCoroutine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GpsCheckerImpl @Inject constructor(
    private val externalCoroutine: ExternalCoroutine,
    private val locationManager: LocationManager,
) : GpsChecker {

    private val _hasGps = MutableStateFlow(locationManager.isLocationEnabled)
    override val hasGps: StateFlow<Boolean>
    get() = _hasGps

    override fun refreshStatus() {
        externalCoroutine.launch {
            _hasGps.emit(locationManager.isLocationEnabled)
        }
    }
}
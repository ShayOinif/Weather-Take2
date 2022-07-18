package com.shayo.weather.utils.gps

import kotlinx.coroutines.flow.StateFlow

interface GpsChecker {
    val hasGps: StateFlow<Boolean>

    fun refreshStatus()
}
package com.shayo.weather.utils.gps

import kotlinx.coroutines.flow.SharedFlow

interface GpsChecker {
    val hasGps: SharedFlow<Boolean>

    fun registerListener()

    fun unRegisterListener()
}
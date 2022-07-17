package com.shayo.weather.utils.networkstatuschecker

import kotlinx.coroutines.flow.SharedFlow

interface NetworkStatusChecker {

    val hasInternet: SharedFlow<Boolean>

    fun registerListener()

    fun unRegisterListener()
}
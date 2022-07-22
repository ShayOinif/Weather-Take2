package com.shayo.weather.service

import android.content.Intent
import android.content.res.Configuration
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.shayo.weather.domain.RefreshCurrentWeatherUseCase
import com.shayo.weather.utils.logger.MyLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "WeatherService"
private const val WEATHER_SERVICE_REFRESHING = "Weather service started refreshing"
private const val WEATHER_SERVICE_STOP_REFRESHING = "Weather service stopped refreshing"
private const val DEFAULT_REFRESH = 10_000L

@AndroidEntryPoint
class WeatherService : LifecycleService() {

    private var running = false

    private var refreshJob: Job? = null

    private var configurationChange = false

    private var numberOfConnections = 0

    private val localBinder = LocalBinder()

    @Inject
    lateinit var refreshCurrentWeatherUseCase: RefreshCurrentWeatherUseCase

    @Inject
    lateinit var myLogger: MyLogger

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        numberOfConnections++
        configurationChange = false
        return localBinder
    }

    override fun onRebind(intent: Intent) {
        numberOfConnections++
        configurationChange = false
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent): Boolean {
        if (!configurationChange) {
            numberOfConnections--

            if (numberOfConnections == 0) {
                stopRefreshing()

                stopSelf()
            }
        }

        // Ensures onRebind() is called if MainActivity (client) rebinds.
        return true
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy()")
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        configurationChange = true
    }

    fun refreshWeather(interval: Long) {
        if (!running) {
            myLogger.logInfo(TAG, WEATHER_SERVICE_REFRESHING)

            running = true
            refreshJob = lifecycleScope.launch (Dispatchers.IO) {
                while (running) {
                    myLogger.logInfo(TAG, "Running refresh")
                    refreshCurrentWeatherUseCase.invoke()

                    if (running)
                        delay(interval)
                }
            }
        }
    }

    fun stopRefreshing() {
        myLogger.logInfo(TAG, WEATHER_SERVICE_STOP_REFRESHING)

        refreshJob?.cancel()
        running = false
    }

    inner class LocalBinder : Binder() {
        internal val service: WeatherService
            get() = this@WeatherService
    }
}
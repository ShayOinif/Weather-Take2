package com.shayo.weather.service

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.shayo.weather.domain.RefreshCurrentWeatherUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "WeatherService"

@AndroidEntryPoint
class WeatherService : LifecycleService() {

    private val weatherServiceBinder = WeatherServiceBinder()

    private var weatherRefreshJob: Job? = null
    private var running = false

    @Inject
    lateinit var refreshCurrentWeatherUseCase: RefreshCurrentWeatherUseCase

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)

        return weatherServiceBinder
    }

    private fun refreshWeather(interval: Long) {
        Log.d(TAG, "refreshWeather()")

        if (weatherRefreshJob == null) {
            running = true
            weatherRefreshJob = lifecycleScope.launch {
                while (running) {
                    Log.d("Shay", "collecting")
                    refreshCurrentWeatherUseCase.invoke()

                    if (running)
                        delay(interval)
                }
            }
        }
    }

    inner class WeatherServiceBinder : Binder() {
        internal fun refreshWeather(interval: Long) =
            this@WeatherService.refreshWeather(interval)
    }
}
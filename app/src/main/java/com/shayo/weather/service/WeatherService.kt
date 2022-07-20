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

    @Inject
    lateinit var refreshCurrentWeatherUseCase: RefreshCurrentWeatherUseCase

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)

        return weatherServiceBinder
    }

    private fun refreshWeather(interval: Long) {
        Log.d(TAG, "refreshWeather()")

        if (weatherRefreshJob == null) {
            weatherRefreshJob = lifecycleScope.launch {
                var success = true

                while (success) {
                    Log.d("Shay", "collecting")
                    refreshCurrentWeatherUseCase.invoke().collect {
                        if (it.isFailure) {
                            success = false
                            weatherRefreshJob?.cancel()
                        } else {
                            delay(interval)
                        }
                    }
                }
            }
        }
    }

    inner class WeatherServiceBinder : Binder() {
        internal fun refreshWeather(interval: Long) =
            this@WeatherService.refreshWeather(interval)
    }
}
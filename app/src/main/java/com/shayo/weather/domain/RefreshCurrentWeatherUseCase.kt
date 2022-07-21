package com.shayo.weather.domain

import com.shayo.weather.data.WeatherManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RefreshCurrentWeatherUseCase @Inject constructor(
    private val weatherManager: WeatherManager
) {
    suspend operator fun invoke() =
        withContext(Dispatchers.IO) {
            weatherManager.refreshCurrentWeather()
        }
}
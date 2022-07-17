package com.shayo.weather.domain.weathermanager

import android.location.Location
import com.shayo.weather.data.weather.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherManager {
    fun getLatestLocalWeather(refreshInterval: Long): Flow<Result<Weather>>

    fun getWeatherByLocation(location: Location): Flow<Result<Weather>>

    suspend fun refreshCurrentLocalWeather(): Result<Nothing?>
}
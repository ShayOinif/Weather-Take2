package com.shayo.weather.data

import com.shayo.weather.data.database.LocalLocation
import com.shayo.weather.data.database.LocalWeather
import com.shayo.weather.data.weather.remote.TempUnits
import kotlinx.coroutines.flow.Flow

interface WeatherManager {
    val currentWeather: Flow<Result<LocalWeather>>

    suspend fun getWeatherByLocation(
        localLocation: LocalLocation,
        tempUnits: TempUnits
    ): Result<LocalWeather>

    suspend fun refreshCurrentWeather(): Result<Nothing?>
}
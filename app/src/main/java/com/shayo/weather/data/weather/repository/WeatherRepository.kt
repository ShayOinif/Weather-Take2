package com.shayo.weather.data.weather.repository

import android.location.Location
import com.shayo.weather.data.weather.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    val cachedWeather: Flow<Result<Weather?>>

    fun getWeatherByLocation(
        location: Location,
    ): Flow<Result<Weather>>

    suspend fun saveWeatherToCache(
        weather: Weather
    ): Result<Nothing?>
}
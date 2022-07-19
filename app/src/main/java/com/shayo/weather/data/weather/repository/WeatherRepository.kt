package com.shayo.weather.data.weather.repository

import com.shayo.weather.data.location.model.Location
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
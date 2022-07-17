package com.shayo.weather.data.weather.local

import com.shayo.weather.data.weather.model.Weather
import kotlinx.coroutines.flow.Flow

interface LocalWeatherDatasource {
    val currentWeather: Flow<Result<Weather?>>

    suspend fun insertWeather(weather: Weather): Result<Nothing?>

    suspend fun deleteAll(): Result<Nothing?>
}
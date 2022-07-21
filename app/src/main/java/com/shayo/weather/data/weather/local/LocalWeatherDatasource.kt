package com.shayo.weather.data.weather.local

import com.shayo.weather.data.database.LocalWeather
import kotlinx.coroutines.flow.Flow

interface LocalWeatherDatasource {
    val currentWeather: Flow<Result<LocalWeather>>

    suspend fun insertWeather(localWeather: LocalWeather): Result<Nothing?>

    suspend fun clear(): Result<Nothing?>
}
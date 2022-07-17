package com.shayo.weather.data.weather.network

import com.shayo.weather.data.weather.model.Weather
import com.shayo.weather.data.weather.network.model.TempUnits
import kotlinx.coroutines.flow.Flow

interface RemoteWeatherDatasource {
    fun getLatestWeather(
        lat: Double,
        lng: Double,
        tempUnits: TempUnits = TempUnits.Metric()
    ): Flow<Result<Weather>>
}
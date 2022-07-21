package com.shayo.weather.data.weather.repository.mediator

import com.shayo.weather.data.database.LocalLocation
import com.shayo.weather.data.weather.remote.RemoteWeather
import com.shayo.weather.data.weather.remote.TempUnits

interface WeatherMediator {
    suspend fun getWeatherByLocation(
        localLocation: LocalLocation,
        tempUnits: TempUnits = TempUnits.Si,
    ): Result<RemoteWeather>
}
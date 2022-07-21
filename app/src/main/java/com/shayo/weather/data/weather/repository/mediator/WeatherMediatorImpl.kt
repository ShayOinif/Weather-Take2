package com.shayo.weather.data.weather.repository.mediator

import com.shayo.weather.data.database.LocalLocation
import com.shayo.weather.data.weather.remote.RemoteWeatherDatasource
import com.shayo.weather.data.weather.remote.TempUnits
import javax.inject.Inject

class WeatherMediatorImpl @Inject constructor(
    private val remoteWeatherDatasource: RemoteWeatherDatasource,
) : WeatherMediator {
    override suspend fun getWeatherByLocation(
        localLocation: LocalLocation,
        tempUnits: TempUnits
    ) =
        remoteWeatherDatasource.getWeatherByCoordinates(
            localLocation.lat,
            localLocation.lng,
            tempUnits
        )
}

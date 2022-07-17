package com.shayo.weather.data.weather.repository.mediator

import android.location.Location
import com.shayo.weather.data.weather.network.RemoteWeatherDatasource
import com.shayo.weather.data.weather.network.model.TempUnits
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherMediatorImpl @Inject constructor(
    private val remoteWeatherDatasource: RemoteWeatherDatasource,
) : WeatherMediator {
    override fun getWeatherByLocation(
        location: Location,
        tempUnits: TempUnits
    ) =
        remoteWeatherDatasource.getLatestWeather(
            location.latitude,
            location.longitude,
            tempUnits
        ).flowOn(Dispatchers.IO)
}

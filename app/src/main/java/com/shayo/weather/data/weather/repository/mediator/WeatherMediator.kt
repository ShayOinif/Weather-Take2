package com.shayo.weather.data.weather.repository.mediator

import com.shayo.weather.data.location.model.Location
import com.shayo.weather.data.weather.model.Weather
import com.shayo.weather.data.weather.network.model.TempUnits
import kotlinx.coroutines.flow.Flow

interface WeatherMediator {
    fun getWeatherByLocation(
        location: Location,
        tempUnits: TempUnits = TempUnits.Metric(),
    ): Flow<Result<Weather>>
}
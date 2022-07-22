package com.shayo.weather.ui.utils

import com.shayo.weather.model.Weather
import com.shayo.weather.ui.model.WeatherWithAddress
import kotlinx.coroutines.flow.Flow

interface WeatherWithStreetMapper {
    fun mapToWeatherWithStreet(weather: Weather): Flow<WeatherWithAddress>
}
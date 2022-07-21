package com.shayo.weather.ui.utils

import com.shayo.weather.model.Weather
import com.shayo.weather.ui.model.WeatherWithAddress

interface WeatherWithStreetMapper {
    fun mapToWeatherWithStreet(weather: Weather): WeatherWithAddress
}
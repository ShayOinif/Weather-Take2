package com.shayo.weather.domain.utils

import com.shayo.weather.data.database.LocalWeather
import com.shayo.weather.model.Weather

interface WeatherMapper {
    fun convertDataToDomain(localWeather: LocalWeather): Weather
}
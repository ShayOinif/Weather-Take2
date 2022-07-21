package com.shayo.weather.domain.utils

import com.shayo.weather.data.database.LocalWeather
import com.shayo.weather.model.Weather
import javax.inject.Inject

class WeatherMapperImpl @Inject constructor() : WeatherMapper {
    override fun convertDataToDomain(localWeather: LocalWeather) =
        localWeather.run {
            Weather(
                lat,
                lng,
                temperature,
                summary,
                icon
            )
        }
}
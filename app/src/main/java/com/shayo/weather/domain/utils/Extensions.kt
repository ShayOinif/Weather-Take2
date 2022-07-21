package com.shayo.weather.domain.utils

import com.shayo.weather.data.database.LocalWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<Result<LocalWeather>>.map(weatherMapper: WeatherMapper) =
    map { localWeatherResult ->
        localWeatherResult
            .map { localWeather ->
                weatherMapper.convertDataToDomain(
                    localWeather
                )
            }
    }
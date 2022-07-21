package com.shayo.weather.domain

import com.shayo.weather.data.WeatherManager
import com.shayo.weather.domain.utils.WeatherMapper
import com.shayo.weather.domain.utils.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherManager: WeatherManager,
    private val weatherMapper: WeatherMapper,
) {
    operator fun invoke() =
        weatherManager.currentWeather
            .map(weatherMapper)
            .flowOn(Dispatchers.IO)
}
package com.shayo.weather.domain

import com.shayo.weather.data.weather.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetLocalWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke() =
        weatherRepository.cachedWeather.flowOn(Dispatchers.IO)
}
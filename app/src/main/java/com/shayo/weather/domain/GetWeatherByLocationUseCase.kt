package com.shayo.weather.domain

import com.shayo.weather.data.location.model.Location
import com.shayo.weather.data.weather.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetWeatherByLocationUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    operator fun invoke(location: Location) =
        weatherRepository.getWeatherByLocation(location).flowOn(Dispatchers.IO)
}
package com.shayo.weather.domain

import com.shayo.weather.data.weather.model.Weather
import com.shayo.weather.data.weather.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(weather: Weather) =
        withContext(Dispatchers.IO) {
            weatherRepository.saveWeatherToCache(weather)
        }
}
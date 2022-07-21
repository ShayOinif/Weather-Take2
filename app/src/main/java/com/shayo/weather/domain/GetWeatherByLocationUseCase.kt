package com.shayo.weather.domain

import com.shayo.weather.data.WeatherManager
import com.shayo.weather.data.database.LocalLocation
import com.shayo.weather.data.weather.remote.TempUnits
import com.shayo.weather.domain.utils.WeatherMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherByLocationUseCase @Inject constructor(
    private val weatherManager: WeatherManager,
    private val weatherMapper: WeatherMapper
) {
    suspend operator fun invoke(localLocation: LocalLocation, tempUnits: TempUnits) =
        withContext(Dispatchers.IO) {
            weatherManager.getWeatherByLocation(localLocation, tempUnits)
                .map {
                    weatherMapper.convertDataToDomain(it)
                }
        }
}
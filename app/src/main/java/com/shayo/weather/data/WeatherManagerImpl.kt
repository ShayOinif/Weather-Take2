package com.shayo.weather.data

import com.shayo.weather.data.database.LocalLocation
import com.shayo.weather.data.location.repository.LocationRepository
import com.shayo.weather.data.weather.remote.TempUnits
import com.shayo.weather.data.weather.repository.WeatherRepository
import javax.inject.Inject

class WeatherManagerImpl @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
) : WeatherManager {
    override val currentWeather =
        weatherRepository.currentWeather

    override suspend fun getWeatherByLocation(
        localLocation: LocalLocation,
        tempUnits: TempUnits
    ) =
        weatherRepository.getWeatherByLocation(
            localLocation,
            tempUnits
        )

    override suspend fun refreshCurrentWeather() =
        locationRepository.refreshCurrentLocation()
            .mapCatching { refreshedLocation ->
                weatherRepository.refreshCurrentWeather(
                    refreshedLocation,
                ).fold(
                    {
                        it
                    },
                    {
                        throw it
                    }
                )
            }
}
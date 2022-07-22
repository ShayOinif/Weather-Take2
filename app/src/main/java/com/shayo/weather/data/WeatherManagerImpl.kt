package com.shayo.weather.data

import android.util.Log
import com.shayo.weather.data.database.LocalLocation
import com.shayo.weather.data.location.repository.LocationRepository
import com.shayo.weather.data.weather.remote.TempUnits
import com.shayo.weather.data.weather.repository.WeatherRepository
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
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
            .recoverCatching {
                Log.d("Shay", "No remote location in here")
                val test = locationRepository.currentLocation.take(1).last().getOrNull()!!
                Log.d("Shay", "No remote location in here, local location: $test")
                test
            }.fold(
                { refreshedLocation ->
                    Log.d("Shay", "location to query weather of is ${refreshedLocation.toString()}")
                    weatherRepository.refreshCurrentWeather(
                        refreshedLocation,
                    ).fold(
                        {
                            Result.success(it)
                        },
                        {
                            Result.failure(it)
                        }
                    )
                }
            ,
                {
                    Log.d("Shay", "A problem $it.message.toString()}")
                    Result.failure(it)
                }
            )
}
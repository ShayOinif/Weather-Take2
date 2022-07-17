package com.shayo.weather.domain.weathermanager

import android.location.Location
import com.shayo.weather.data.location.repository.LocationRepository
import com.shayo.weather.data.weather.model.Weather
import com.shayo.weather.data.weather.repository.WeatherRepository
import com.shayo.weather.utils.logger.MyLogger
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "WeatherManagerImpl"

class WeatherManagerImpl @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository,
    private val myLogger: MyLogger
): WeatherManager {
    override fun getLatestLocalWeather(refreshInterval: Long): Flow<Result<Weather>> {
      /*  locationRepository.getCurrentLocation().flatMapConcat { location ->
            weatherRepository.
                .getLatestWeather(location.getOrNull()!!.latitude, location.getOrNull()!!.longitude).map {
                WeatherByLocation(location.getOrNull()!!.latitude, location.getOrNull()!!.longitude, it.getOrNull()!!)
            }.onEach {
                    delay(refreshInterval)
                refreshCurrentLocalWeather()
                }
        }*/
        TODO()
    }

    override fun getWeatherByLocation(location: Location) =
        weatherRepository.getWeatherByLocation(location)

    override suspend fun refreshCurrentLocalWeather() =
        /*withContext(Dispatchers.IO) {

            var refreshResult = Result.failure<Nothing?>(WeatherAppThrowable.UnexpectedError())

            locationRepository.getCurrentLocation().collect { locationResult ->
                locationResult.onSuccess { location ->
                    weatherRepository.refreshWeather(location)
                        .onSuccess {
                            refreshResult = Result.success(null)
                        }.onFailure {
                            myLogger.logError(TAG, "Error refreshing location")

                            refreshResult = Result.failure(it)
                        }
                }.onFailure {
                    myLogger.logError(TAG, "Error getting location")

                    refreshResult = Result.failure(it)
                }
            }

            refreshResult
        }*/
        TODO()
}

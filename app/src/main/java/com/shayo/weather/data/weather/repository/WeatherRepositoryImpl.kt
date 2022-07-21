package com.shayo.weather.data.weather.repository

import com.shayo.weather.data.database.LocalLocation
import com.shayo.weather.data.database.LocalWeather
import com.shayo.weather.data.weather.local.LocalWeatherDatasource
import com.shayo.weather.data.weather.remote.RemoteWeather
import com.shayo.weather.data.weather.remote.TempUnits
import com.shayo.weather.data.weather.repository.mediator.WeatherMediator
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherMediator: WeatherMediator,
    private val localWeatherDatasource: LocalWeatherDatasource,
) : WeatherRepository {
    override val currentWeather =
        localWeatherDatasource.currentWeather

    override suspend fun getWeatherByLocation(
        localLocation: LocalLocation,
        tempUnits: TempUnits
    ) =
        weatherMediator.getWeatherByLocation(localLocation, tempUnits)
            .map { remoteWeather ->
                remoteWeather.mapToLocal()
            }

    override suspend fun refreshCurrentWeather(
        localLocation: LocalLocation,
        tempUnits: TempUnits
    ) =
        getWeatherByLocation(
            localLocation,
            tempUnits
        ).fold(
            onSuccess = { newWeather ->
                localWeatherDatasource.clear()
                    .fold(
                        onSuccess = {
                            localWeatherDatasource.insertWeather(
                                newWeather
                            )
                        },
                        onFailure = {
                            Result.failure(it)
                        }
                    )
            },
            onFailure = {
                Result.failure(it)
            }
        )
}

private fun RemoteWeather.mapToLocal() =
    LocalWeather(
        lat,
        temperature,
        windBearing,
        windSpeed,
        apparentTemperature,
        dewPoint,
        pressure,
        windGust,
        cloudCover,
        visibility,
        ozone,
        humidity,
        uvIndex,
        time,
        summary,
        icon,
        lng,
        precipIntensity
    )
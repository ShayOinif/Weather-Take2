package com.shayo.weather.data.weather.repository

import com.shayo.weather.data.location.model.Location
import com.shayo.weather.data.weather.local.LocalWeatherDatasource
import com.shayo.weather.data.weather.model.Weather
import com.shayo.weather.data.weather.repository.mediator.WeatherMediator
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherMediator: WeatherMediator,
    private val localWeatherDatasource: LocalWeatherDatasource,
) : WeatherRepository {
    override val cachedWeather =
        localWeatherDatasource.currentWeather

    override fun getWeatherByLocation(location: Location) =
        weatherMediator.getWeatherByLocation(location)

    override suspend fun saveWeatherToCache(weather: Weather) =
        localWeatherDatasource.deleteAll().fold(
            {
                localWeatherDatasource.insertWeather(weather)
            },
            {
                Result.failure(it)
            }
        )
}
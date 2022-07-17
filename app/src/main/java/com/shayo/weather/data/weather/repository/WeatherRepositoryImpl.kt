package com.shayo.weather.data.weather.repository

import android.location.Location
import com.shayo.weather.data.weather.local.LocalWeatherDatasource
import com.shayo.weather.data.weather.model.Weather
import com.shayo.weather.data.weather.repository.mediator.WeatherMediator
import com.shayo.weather.utils.logger.MyLogger
import javax.inject.Inject

private const val TAG = "WeatherRepositoryImpl"
private const val NO_CACHE = "Mo cache, reverting to remote"

class WeatherRepositoryImpl @Inject constructor(
    private val weatherMediator: WeatherMediator,
    private val localWeatherDatasource: LocalWeatherDatasource,
    private val myLogger: MyLogger
) : WeatherRepository {
    override val cachedWeather =
        localWeatherDatasource.currentWeather

    override fun getWeatherByLocation(location: Location) =
        weatherMediator.getWeatherByLocation(location)

    override suspend fun saveWeatherToCache(weather: Weather) =
        localWeatherDatasource.insertWeather(weather)
}
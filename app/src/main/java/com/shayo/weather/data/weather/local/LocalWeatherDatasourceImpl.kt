package com.shayo.weather.data.weather.local

import com.shayo.weather.data.database.WeatherDao
import com.shayo.weather.data.weather.model.Weather
import com.shayo.weather.model.WeatherAppThrowable
import com.shayo.weather.utils.logger.MyLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "LocalWeatherDatasourceImpl"

class LocalWeatherDatasourceImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    private val myLogger: MyLogger,
) : LocalWeatherDatasource {
    override val currentWeather =
        weatherDao.getWeather()
            .map {
                Result.success(it)
            }
            .catch { cause ->
                myLogger.logError(TAG, cause.message)
                emit(Result.failure(WeatherAppThrowable.InternalError(cause.message)))
            }.flowOn(Dispatchers.IO)

    override suspend fun insertWeather(weather: Weather) =
        performCatching {
            weatherDao.insertWeather(weather)
        }

    override suspend fun deleteAll() =
        performCatching {
            weatherDao.nukeTable()
        }

    private suspend fun performCatching(
        block: suspend () -> Unit
    ) =
        withContext(Dispatchers.IO) {
            try {
                block()
                Result.success(null)
            } catch (t: Throwable) {
                myLogger.logError(TAG, t.message)
                Result.failure(WeatherAppThrowable.InternalError(t.message))
            }
        }
}

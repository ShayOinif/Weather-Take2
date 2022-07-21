package com.shayo.weather.data.weather.local

import com.shayo.weather.data.database.LocalWeather
import com.shayo.weather.data.database.WeatherDao
import com.shayo.weather.data.utils.performCatching
import com.shayo.weather.utils.WeatherAppThrowable
import com.shayo.weather.utils.logger.MyLogger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "LocalWeatherDatasourceImpl"

class LocalWeatherDatasourceImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    private val myLogger: MyLogger,
) : LocalWeatherDatasource {
    override val currentWeather =
        weatherDao.getCurrentWeather()
            .map { localWeather ->
                localWeather?.let {
                    Result.success(it)
                } ?: Result.failure(WeatherAppThrowable.NoCurrentWeather)
            }
            .catch { cause ->
                myLogger.logError(TAG, cause.message)
                emit(Result.failure(WeatherAppThrowable.InternalError(cause.message)))
            }

    override suspend fun insertWeather(localWeather: LocalWeather) =
        performCatching(
            myLogger,
            TAG,
        ) {
            weatherDao.insertWeather(localWeather)
        }

    override suspend fun clear() =
        performCatching(
            myLogger,
            TAG,
        ) {
            weatherDao.clear()
        }
}

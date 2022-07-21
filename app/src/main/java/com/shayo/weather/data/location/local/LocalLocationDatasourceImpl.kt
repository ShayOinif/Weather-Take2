package com.shayo.weather.data.location.local

import com.shayo.weather.data.database.LocalLocation
import com.shayo.weather.data.database.LocationDao
import com.shayo.weather.data.utils.performCatching
import com.shayo.weather.utils.WeatherAppThrowable
import com.shayo.weather.utils.logger.MyLogger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "LocalLocationDatasourceImpl"
private const val DEFAULT_LAT = 34.809626
private const val DEFAULT_LNG = 32.160152

class LocalLocationDatasourceImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val myLogger: MyLogger,
) : LocalLocationDatasource {
    override val currentLocation =
        locationDao.getCurrentLocation()
            .map { cachedLocation ->
                Result.success(cachedLocation ?: getDefaultLocation())
            }
            .catch { cause ->
                myLogger.logError(TAG, cause.message)
                emit(Result.failure(WeatherAppThrowable.InternalError(cause.message)))
            }

    override suspend fun insertLocation(
        localLocation: LocalLocation,
    ) =
        performCatching(
            myLogger,
            TAG,
        ) {
            locationDao.insertLocation(localLocation)
        }

    override suspend fun clear() =
        performCatching(
            myLogger,
            TAG,
        ) {
            locationDao.clear()
        }
}

private fun getDefaultLocation() =
    LocalLocation(
        DEFAULT_LAT,
        DEFAULT_LNG,
        System.currentTimeMillis()
    )


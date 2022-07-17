package com.shayo.weather.data.location.local

import com.shayo.weather.data.database.LocationDao
import com.shayo.weather.data.location.model.Location
import com.shayo.weather.model.WeatherAppThrowable
import com.shayo.weather.utils.logger.MyLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "LocalLocationDatasourceImpl"

class LocalLocationDatasourceImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val myLogger: MyLogger,
) : LocalLocationDatasource {
    override fun getLocation() =
        locationDao.getLocation()
            .map { cachedLocation ->
                Result.success(cachedLocation ?: getDefaultLocation())
            }
            .catch { cause ->
                myLogger.logError(TAG, cause.message)
                emit(Result.failure(WeatherAppThrowable.InternalError(cause.message)))
            }.flowOn(Dispatchers.IO)

    override suspend fun insertLocation(location: Location) =
        performCatching {
            locationDao.insertLocation(location)
        }

    override suspend fun deleteAll() =
        performCatching {
            locationDao.nukeTable()
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

private fun getDefaultLocation() =
    Location(
        34.809626,
        32.160152
    )


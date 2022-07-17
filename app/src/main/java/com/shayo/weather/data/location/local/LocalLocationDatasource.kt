package com.shayo.weather.data.location.local

import com.shayo.weather.data.location.model.Location
import kotlinx.coroutines.flow.Flow

interface LocalLocationDatasource {
    fun getLocation(): Flow<Result<Location>>

    suspend fun insertLocation(location: Location): Result<Nothing?>

    suspend fun deleteAll(): Result<Nothing?>
}
package com.shayo.weather.data.location.local

import com.shayo.weather.data.database.LocalLocation
import kotlinx.coroutines.flow.Flow

interface LocalLocationDatasource {
    val currentLocation: Flow<Result<LocalLocation>>

    suspend fun insertLocation(localLocation: LocalLocation): Result<Nothing?>

    suspend fun clear(): Result<Nothing?>
}
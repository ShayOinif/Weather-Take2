package com.shayo.weather.data.location.repository

import com.shayo.weather.data.database.LocalLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    val currentLocation: Flow<Result<LocalLocation>>

    suspend fun refreshCurrentLocation(): Result<LocalLocation>
}
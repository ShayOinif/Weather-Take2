package com.shayo.weather.data.location.repository

import com.shayo.weather.data.location.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getCurrentLocation(): Flow<Result<Location>>
}
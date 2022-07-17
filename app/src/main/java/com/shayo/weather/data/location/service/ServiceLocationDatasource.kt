package com.shayo.weather.data.location.service

import com.shayo.weather.data.location.model.Location
import kotlinx.coroutines.flow.Flow

interface ServiceLocationDatasource {
    fun getCurrentLocation(): Flow<Result<Location>>
}
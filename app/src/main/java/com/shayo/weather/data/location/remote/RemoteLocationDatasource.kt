package com.shayo.weather.data.location.remote

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface RemoteLocationDatasource {
    fun getCurrentLocation(): Flow<Result<Location>>
}
package com.shayo.weather.data.location.repository.mediator

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationMediator {
    fun getNewLocation(): Flow<Result<Location>>
}